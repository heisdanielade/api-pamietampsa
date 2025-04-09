package com.github.heisdanielade.pamietampsa.exception.auth;

import com.github.heisdanielade.pamietampsa.exception.AuthenticationException;

public class AccountNotFoundException extends AuthenticationException {
    public AccountNotFoundException() {
        super("Account does not exist.");
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
