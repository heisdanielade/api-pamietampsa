package com.github.heisdanielade.pamietampsa.exception.auth;

public class EmailAlreadyInUseException extends AuthenticationException {
    public EmailAlreadyInUseException() {
        super("Email is already in use.");
    }
}

