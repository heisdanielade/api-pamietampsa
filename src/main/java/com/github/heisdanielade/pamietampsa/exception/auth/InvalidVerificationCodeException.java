package com.github.heisdanielade.pamietampsa.exception.auth;

public class InvalidVerificationCodeException extends AuthenticationException{
    public InvalidVerificationCodeException() {
        super("Invalid Verification code.");
    }
}
