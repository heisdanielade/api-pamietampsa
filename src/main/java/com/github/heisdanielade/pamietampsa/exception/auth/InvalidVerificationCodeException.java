package com.github.heisdanielade.pamietampsa.exception.auth;

import com.github.heisdanielade.pamietampsa.exception.CustomAuthenticationException;

public class InvalidVerificationCodeException extends CustomAuthenticationException {
    public InvalidVerificationCodeException() {
        super("Invalid Verification code.");
    }

    public InvalidVerificationCodeException(String message) {
        super(message);
    }
}
