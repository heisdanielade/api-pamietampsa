package com.github.heisdanielade.pamietampsa.exception.auth;

import com.github.heisdanielade.pamietampsa.exception.CustomException;

public class AccountNotVerifiedException extends CustomException {
    public AccountNotVerifiedException() {
        super("Account disabled, verify your email");
    }

    public AccountNotVerifiedException(String message) {
        super(message);
    }
}
