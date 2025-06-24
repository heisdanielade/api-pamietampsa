package com.github.heisdanielade.pamietampsa.exception.other;

import com.github.heisdanielade.pamietampsa.exception.CustomException;

public class NoChangesMadeException extends CustomException {
    public NoChangesMadeException() {
        super("No changes made");
    }

    public NoChangesMadeException(String message) {
        super(message);
    }
}