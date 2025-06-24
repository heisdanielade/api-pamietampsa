package com.github.heisdanielade.pamietampsa.exception.auth;

import com.github.heisdanielade.pamietampsa.exception.CustomException;

public class AccountAlreadyVerifiedException extends CustomException {
    public AccountAlreadyVerifiedException() {
        super("Account already verified");
    }

    public AccountAlreadyVerifiedException(String message) {
        super(message);
    }
}
