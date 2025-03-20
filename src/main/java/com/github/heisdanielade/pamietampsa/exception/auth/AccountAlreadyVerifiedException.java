package com.github.heisdanielade.pamietampsa.exception.auth;

public class AccountAlreadyVerifiedException extends AuthenticationException{
    public AccountAlreadyVerifiedException() {
        super("Account has already been verified.");
    }
}
