package com.github.heisdanielade.pamietampsa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/v1/")
public class UserController {

    @GetMapping(path = "/hello")
    public String hello(){
        return "Hello World!";
    }
}
