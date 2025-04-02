package com.github.heisdanielade.pamietampsa.exception.auth;

public class UserNotFoundException extends AuthenticationException {
    public UserNotFoundException() {
        super("User does not exist.");
    }
}
