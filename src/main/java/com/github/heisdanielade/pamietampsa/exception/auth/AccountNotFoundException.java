package com.github.heisdanielade.pamietampsa.exception.auth;

import com.github.heisdanielade.pamietampsa.exception.CustomException;

public class AccountNotFoundException extends CustomException {
    public AccountNotFoundException() {
        super("Account does not exist");
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
