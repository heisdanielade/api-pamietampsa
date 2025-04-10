package com.github.heisdanielade.pamietampsa.exception.auth;

import com.github.heisdanielade.pamietampsa.exception.CustomAuthenticationException;

public class AccountNotFoundException extends CustomAuthenticationException {
    public AccountNotFoundException() {
        super("Account does not exist.");
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
