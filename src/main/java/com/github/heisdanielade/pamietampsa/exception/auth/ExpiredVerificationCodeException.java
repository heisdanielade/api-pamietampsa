package com.github.heisdanielade.pamietampsa.exception.auth;

public class ExpiredVerificationCodeException extends AuthenticationException {
    public ExpiredVerificationCodeException() {
        super("Account Verification code has expired.");
    }
}

