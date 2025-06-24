package com.github.heisdanielade.pamietampsa.exception.auth;

import com.github.heisdanielade.pamietampsa.exception.CustomException;

public class InvalidVerificationCodeException extends CustomException {
    public InvalidVerificationCodeException() {
        super("Invalid verification code");
    }

    public InvalidVerificationCodeException(String message) {
        super(message);
    }
}
