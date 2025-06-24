package com.github.heisdanielade.pamietampsa.exception.auth;

import com.github.heisdanielade.pamietampsa.exception.CustomException;

public class ExpiredVerificationCodeException extends CustomException {
    public ExpiredVerificationCodeException() {
        super("Verification code has expired");
    }

    public ExpiredVerificationCodeException(String message) {
        super(message);
    }
}

