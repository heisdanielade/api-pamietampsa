package com.github.heisdanielade.pamietampsa.exception.auth;

import com.github.heisdanielade.pamietampsa.exception.AuthenticationException;

public class InvalidVerificationCodeException extends AuthenticationException {
    public InvalidVerificationCodeException() {
        super("Invalid Verification code.");
    }

    public InvalidVerificationCodeException(String message) {
        super(message);
    }
}
