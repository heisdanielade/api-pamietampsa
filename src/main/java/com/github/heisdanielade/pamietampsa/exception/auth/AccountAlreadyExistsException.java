package com.github.heisdanielade.pamietampsa.exception.auth;

import com.github.heisdanielade.pamietampsa.exception.AuthenticationException;

public class AccountAlreadyExistsException extends AuthenticationException {
    public AccountAlreadyExistsException() {
        super("Account already exists.");
    }

    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}

