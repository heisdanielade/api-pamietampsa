package com.github.heisdanielade.pamietampsa.exception.auth;

import com.github.heisdanielade.pamietampsa.exception.CustomException;

public class InvalidLoginCredentialsException extends CustomException {

    public InvalidLoginCredentialsException(){
        super("Invalid email or password");
    }

    public InvalidLoginCredentialsException(String message){
        super(message);
    }
}

