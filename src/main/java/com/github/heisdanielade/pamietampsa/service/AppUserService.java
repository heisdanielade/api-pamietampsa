package com.github.heisdanielade.pamietampsa.service;

import com.github.heisdanielade.pamietampsa.dto.user.UserPatchDto;
import com.github.heisdanielade.pamietampsa.dto.user.UserResponseDto;
import com.github.heisdanielade.pamietampsa.entity.AppUser;
import com.github.heisdanielade.pamietampsa.exception.auth.AccountNotFoundException;
import com.github.heisdanielade.pamietampsa.exception.other.NoChangesMadeException;
import com.github.heisdanielade.pamietampsa.repository.AppUserRepository;
import com.github.heisdanielade.pamietampsa.util.DtoMapper;
import com.github.heisdanielade.pamietampsa.util.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository userRepository;
    private final EmailSender emailSender;

    // Get all users in the DB
    @Cacheable("users")
    public List<UserResponseDto> getAllUsers(){
        List<AppUser> users = userRepository.findAll();
        return users.stream().map(DtoMapper::toUserDto).toList();
    }

    // Get info of currently logged-in user
    @Cacheable("users")
    public UserResponseDto getUserInfo(Principal principal){
        String userEmail = principal.getName();
        AppUser currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(AccountNotFoundException::new);

        return new UserResponseDto(
                currentUser.getEmail(),
                currentUser.getName(),
                currentUser.getInitial(),
                String.valueOf(currentUser.isEnabled()),
                String.valueOf(currentUser.getRole())
        );
    }

    // Edit user info
    public void editUserInfo(UserPatchDto input, Principal principal){
        String userEmail = principal.getName();
        AppUser currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(AccountNotFoundException::new);

        if (input == null) {
            throw new NoChangesMadeException();
        }

        Optional.ofNullable(input.getEmail())
                .ifPresent(currentUser::setEmail);

        Optional.ofNullable(input.getName())
                .ifPresent(currentUser::setName);

        userRepository.save(currentUser);
//        TODO: send notification email
    }

    // TODO: Delete user
    public void deleteAppUser(AppUser appUser){
        // Set the account expiration date to a 2-week timer
        appUser.setAccountExpirationDate(LocalDate.now().plusDays(14));
    }

}
