package com.github.heisdanielade.pamietampsa.service;

import com.github.heisdanielade.pamietampsa.entity.AppUser;
import com.github.heisdanielade.pamietampsa.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class AppUserService {
//    private final AppUserRepository appUserRepository;

//    @Autowired
//    public AppUserService(AppUserRepository appUserRepository){
//        this.appUserRepository = appUserRepository;
//    }
//

    // Delete user
    public void deleteAppUser(AppUser appUser){
        // Set the account expiration date to 2 weeks timer
        appUser.setAccountExpirationDate(LocalDate.now().plusDays(14));
    }



}
