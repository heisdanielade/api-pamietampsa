package com.github.heisdanielade.pamietampsa.exception.auth;

import com.github.heisdanielade.pamietampsa.exception.CustomAuthenticationException;

public class AccountAlreadyVerifiedException extends CustomAuthenticationException {
    public AccountAlreadyVerifiedException() {
        super("Email already verified.");
    }

    public AccountAlreadyVerifiedException(String message) {
        super(message);
    }
}
