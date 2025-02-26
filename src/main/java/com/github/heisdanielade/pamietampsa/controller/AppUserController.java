package com.github.heisdanielade.pamietampsa.controller;

import com.github.heisdanielade.pamietampsa.entity.AppUser;
import com.github.heisdanielade.pamietampsa.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(path = "/api/v1/")
public class AppUserController {

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping(path = "/hello")
    public String hello(){
        return "Hello World!";
    }


    // Registration endpoint
    @PostMapping(path = "/sign-up")
    public void registerNewAppUser(@RequestBody AppUser appUser){
        appUserService.addNewAppUser(appUser);
    }


}
