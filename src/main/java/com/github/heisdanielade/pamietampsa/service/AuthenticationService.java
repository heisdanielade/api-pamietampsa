package com.github.heisdanielade.pamietampsa.service;

import com.github.heisdanielade.pamietampsa.dto.user.LoginUserDto;
import com.github.heisdanielade.pamietampsa.dto.user.RegisterUserDto;
import com.github.heisdanielade.pamietampsa.dto.user.VerifyUserDto;
import com.github.heisdanielade.pamietampsa.entity.AppUser;
import com.github.heisdanielade.pamietampsa.enums.Role;
import com.github.heisdanielade.pamietampsa.exception.auth.*;
import com.github.heisdanielade.pamietampsa.repository.AppUserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AppUser signup(RegisterUserDto input){
        if (userRepository.findByEmail(input.getEmail()).isPresent()) {
            throw new EmailAlreadyInUseException();
        }

        AppUser user = new AppUser(input.getEmail(), passwordEncoder.encode(input.getPassword()));
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(10));
        user.setEnabled(false);
        user.setRole(Role.USER); // Default role
        sendVerificationEmail(user);

        return userRepository.save(user);
    }

    public AppUser authenticate(LoginUserDto input){
        AppUser user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if(!user.isEnabled()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account not verified. Please check your email.");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
        user.setLastLoginAt(Instant.now());
        userRepository.save(user);
        return user;
    }

    public void verifyUser(VerifyUserDto input){
        Optional<AppUser> optionalUser = userRepository.findByEmail(input.getEmail());

        if(optionalUser.isEmpty()){
            throw new UserNotFoundException();
        }
        AppUser user = optionalUser.get();
        if(user.getVerificationCode() == null){
            // If the user's verification code is null, it means user has been verified since
            // the verificationCode attribute is set to null upon verification.
            throw new AccountAlreadyVerifiedException();
        }
        if(user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())){
            throw new ExpiredVerificationCodeException();
        }
        if(!user.getVerificationCode().equals(input.getOtp())){
            throw new InvalidVerificationCodeException();
        } else{
            user.setEnabled(true);
            user.setVerificationCode(null); // Verification code no longer needed
            user.setVerificationCodeExpiresAt(null);
        }
    }

    private void sendVerificationEmail(AppUser user) {
        String subject = "Account Verification";
        String verificationCode = user.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to PamietamPsa!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

    public void resendVerificationEmail(String email) {
        Optional<AppUser> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            AppUser user = optionalUser.get();
            if(user.isEnabled()){
                throw new AccountAlreadyVerifiedException();
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(5));
            sendVerificationEmail(user);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException();
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000; // Six-digit code
        return String.valueOf(code);
    }


}
