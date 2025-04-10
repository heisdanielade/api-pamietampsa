package com.github.heisdanielade.pamietampsa.exception.auth;

import com.github.heisdanielade.pamietampsa.exception.CustomAuthenticationException;

public class AccountAlreadyExistsException extends CustomAuthenticationException {
    public AccountAlreadyExistsException() {
        super("Account already exists.");
    }

    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}

