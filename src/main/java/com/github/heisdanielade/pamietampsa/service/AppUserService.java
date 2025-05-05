package com.github.heisdanielade.pamietampsa.service;

import com.github.heisdanielade.pamietampsa.entity.AppUser;
import com.github.heisdanielade.pamietampsa.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserService {
    private final AppUserRepository userRepository;

    public AppUserService(AppUserRepository userRepository){
        this.userRepository = userRepository;
    }


    public List<AppUser> allUsers(){
        return new ArrayList<>(userRepository.findAll());
    }

    // Delete user
    public void deleteAppUser(AppUser appUser){
        // Set the account expiration date to a 2-week timer
        appUser.setAccountExpirationDate(LocalDate.now().plusDays(14));
    }

}
