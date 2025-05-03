package com.github.heisdanielade.pamietampsa.exception.pet;

import com.github.heisdanielade.pamietampsa.exception.CustomException;

public class PetAlreadyExistsException extends CustomException {
    public PetAlreadyExistsException(){
        super("Pet already exists.");
    }

    public PetAlreadyExistsException(String message){
        super(message);
    }
}
