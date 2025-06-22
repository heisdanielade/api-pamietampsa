package com.github.heisdanielade.pamietampsa.service;

import com.github.heisdanielade.pamietampsa.dto.user.UserResponseDto;
import com.github.heisdanielade.pamietampsa.entity.AppUser;
import com.github.heisdanielade.pamietampsa.exception.auth.AccountNotFoundException;
import com.github.heisdanielade.pamietampsa.repository.AppUserRepository;
import com.github.heisdanielade.pamietampsa.util.DtoMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserService {
    private final AppUserRepository userRepository;

    public AppUserService(AppUserRepository userRepository){
        this.userRepository = userRepository;
    }

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


    // Delete user
    public void deleteAppUser(AppUser appUser){
        // Set the account expiration date to a 2-week timer
        appUser.setAccountExpirationDate(LocalDate.now().plusDays(14));
    }

}
