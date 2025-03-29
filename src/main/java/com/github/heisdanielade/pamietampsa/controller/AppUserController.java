package com.github.heisdanielade.pamietampsa.controller;

import com.github.heisdanielade.pamietampsa.entity.AppUser;
import com.github.heisdanielade.pamietampsa.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping(path = "/hello")
    public String hello(){
        return "Hello World!";
    }

    @GetMapping("/me")
    public ResponseEntity<AppUser> authenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser currentUser = (AppUser) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }


    @GetMapping("/")
    public ResponseEntity<List<AppUser>> allUsers(){
        List<AppUser> users = appUserService.allUsers();
        return ResponseEntity.ok(users);
    }

}
