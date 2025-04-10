package com.github.heisdanielade.pamietampsa.exception.auth;

import com.github.heisdanielade.pamietampsa.exception.CustomAuthenticationException;

public class InvalidLoginCredentialsException extends CustomAuthenticationException {

    public InvalidLoginCredentialsException(){
        super("Invalid Email or Password.");
    }

    public InvalidLoginCredentialsException(String message){
        super(message);
    }
}

