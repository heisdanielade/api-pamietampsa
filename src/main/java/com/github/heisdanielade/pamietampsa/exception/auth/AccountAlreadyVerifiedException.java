package com.github.heisdanielade.pamietampsa.exception.auth;

import com.github.heisdanielade.pamietampsa.exception.AuthenticationException;

public class AccountAlreadyVerifiedException extends AuthenticationException {
    public AccountAlreadyVerifiedException() {
        super("Email already verified.");
    }

    public AccountAlreadyVerifiedException(String message) {
        super(message);
    }
}
