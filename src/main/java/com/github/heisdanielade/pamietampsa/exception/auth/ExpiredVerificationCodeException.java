package com.github.heisdanielade.pamietampsa.exception.auth;

import com.github.heisdanielade.pamietampsa.exception.AuthenticationException;

public class ExpiredVerificationCodeException extends AuthenticationException {
    public ExpiredVerificationCodeException() {
        super("Verification code has expired.");
    }

    public ExpiredVerificationCodeException(String message) {
        super(message);
    }
}

