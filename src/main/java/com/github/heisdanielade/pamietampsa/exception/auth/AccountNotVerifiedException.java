package com.github.heisdanielade.pamietampsa.exception.auth;

import com.github.heisdanielade.pamietampsa.exception.CustomAuthenticationException;

public class AccountNotVerifiedException extends CustomAuthenticationException {
    public AccountNotVerifiedException() {
        super("Account not verified. Please verify your email.");
    }

    public AccountNotVerifiedException(String message) {
        super(message);
    }
}
