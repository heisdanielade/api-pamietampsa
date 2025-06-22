package com.github.heisdanielade.pamietampsa.service.auth;

import com.github.heisdanielade.pamietampsa.dto.auth.LoginRequestDto;
import com.github.heisdanielade.pamietampsa.dto.auth.SignupRequestDto;
import com.github.heisdanielade.pamietampsa.dto.auth.ResendVerificationEmailRequestDto;
import com.github.heisdanielade.pamietampsa.dto.auth.EmailVerificationRequestDto;
import com.github.heisdanielade.pamietampsa.entity.AppUser;
import com.github.heisdanielade.pamietampsa.enums.Role;
import com.github.heisdanielade.pamietampsa.exception.auth.*;
import com.github.heisdanielade.pamietampsa.repository.AppUserRepository;
import com.github.heisdanielade.pamietampsa.service.EmailService;
import com.github.heisdanielade.pamietampsa.util.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final EmailSender emailSender;

    public AppUser signup(SignupRequestDto input){
        if (userRepository.findByEmail(input.getEmail()).isPresent()) {
            throw new AccountAlreadyExistsException();
        }
        AppUser user = new AppUser(input.getEmail(), passwordEncoder.encode(input.getPassword()));
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(10));
        user.setEnabled(false);
        user.setRole(Role.USER); // Default role
        userRepository.save(user);
        emailSender.sendVerificationEmail(user.getEmail(), user.getVerificationCode());
        return user;
    }


    public AppUser authenticate(LoginRequestDto input){
        AppUser user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(AccountNotFoundException::new);
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
        } catch (DisabledException e){
            throw new AccountNotVerifiedException();
        } catch (AuthenticationException e) {
            throw new InvalidLoginCredentialsException();
        }

        if(!user.isEnabled()){
            throw new AccountNotVerifiedException();
        }
        user.setLastLoginAt(Instant.now());
        userRepository.save(user);
        return user;
    }


    public void verifyUser(EmailVerificationRequestDto input){
        Optional<AppUser> optionalUser = userRepository.findByEmail(input.getEmail());

        if(optionalUser.isEmpty()){
            throw new AccountNotFoundException();
        }
        AppUser user = optionalUser.get();
        if(user.getVerificationCode() == null){
            /*
             * If the user's verification code is null, it means the user has been verified since
             * the verificationCode attribute is set to null upon verification.
             */
            throw new AccountAlreadyVerifiedException();
        }
        if(user.getVerificationCodeExpiresAt() == null || user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())){
            throw new ExpiredVerificationCodeException();
        }
        if(!user.getVerificationCode().equals(input.getOtp())){
            throw new InvalidVerificationCodeException();
        } else{
            user.setEnabled(true);
            user.setVerificationCode(null); // Verification code no longer needed
            user.setVerificationCodeExpiresAt(null);
            userRepository.save(user);
            emailSender.sendUserRegistrationConfirmationEmail(user.getEmail());
        }
    }


    public void resendVerificationEmail(ResendVerificationEmailRequestDto input) {
        Optional<AppUser> optionalUser = userRepository.findByEmail(input.getEmail());
        if(optionalUser.isPresent()){
            AppUser user = optionalUser.get();
            if(user.isEnabled()){
                throw new AccountAlreadyVerifiedException();
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(10));
            emailSender.sendVerificationEmail(user.getEmail(), user.getVerificationCode());
            userRepository.save(user);
        } else {
            throw new AccountNotFoundException();
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000; // Six-digit code
        return String.valueOf(code);
    }


}
