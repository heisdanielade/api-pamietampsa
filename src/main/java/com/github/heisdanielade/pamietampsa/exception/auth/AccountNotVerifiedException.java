package com.github.heisdanielade.pamietampsa.exception.auth;

public class AccountNotVerifiedException extends AuthenticationException {
    public AccountNotVerifiedException() {
        super("Account not verified. Please verify your email.");
    }
}
