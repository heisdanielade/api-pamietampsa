package com.github.heisdanielade.pamietampsa.exception.auth;

import com.github.heisdanielade.pamietampsa.exception.CustomAuthenticationException;

public class ExpiredVerificationCodeException extends CustomAuthenticationException {
    public ExpiredVerificationCodeException() {
        super("Verification code has expired.");
    }

    public ExpiredVerificationCodeException(String message) {
        super(message);
    }
}

