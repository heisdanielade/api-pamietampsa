package com.github.heisdanielade.pamietampsa.exception.media;

import com.github.heisdanielade.pamietampsa.exception.CustomException;

public class FileTooLargeException extends CustomException {
    public FileTooLargeException(String message){
        super(message);
    }


}
