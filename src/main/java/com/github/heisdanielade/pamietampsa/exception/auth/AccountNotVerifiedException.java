package com.github.heisdanielade.pamietampsa.exception.auth;

import com.github.heisdanielade.pamietampsa.exception.AuthenticationException;

public class AccountNotVerifiedException extends AuthenticationException {
    public AccountNotVerifiedException() {
        super("Account not verified. Please verify your email.");
    }

    public AccountNotVerifiedException(String message) {
        super(message);
    }
}
