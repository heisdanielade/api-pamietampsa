package com.github.heisdanielade.pamietampsa.service;

import com.github.heisdanielade.pamietampsa.entity.AppUser;
import com.github.heisdanielade.pamietampsa.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository){
        this.appUserRepository = appUserRepository;
    }
    
    // Register new user
    public void registerNewAppUser(AppUser appUser){
        Optional<AppUser> appUserOptionalEmail = appUserRepository.findUserByEmail(appUser.getEmail());
        Optional<AppUser> appUserOptionalUsername = appUserRepository.findUserByUsername(appUser.getUsername());

        // Validations - email, username, password
        if (appUserOptionalEmail.isPresent()){
            throw new IllegalStateException("(e) Email already taken.");
        } else if (appUserOptionalUsername.isPresent()){
            throw new IllegalStateException("(e) Username already taken.");
        } else if (appUser.getPassword().length() < 6){
            throw new IllegalStateException("(e) Password is too short, min. (6 chars)");
        }
        appUserRepository.save(appUser);

    }
}
