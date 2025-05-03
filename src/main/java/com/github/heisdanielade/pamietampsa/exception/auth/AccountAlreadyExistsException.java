package com.github.heisdanielade.pamietampsa.exception.auth;

import com.github.heisdanielade.pamietampsa.exception.CustomException;

public class AccountAlreadyExistsException extends CustomException {
    public AccountAlreadyExistsException() {
        super("Account already exists.");
    }

    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}

