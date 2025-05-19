package com.github.heisdanielade.pamietampsa.exception;

import com.github.heisdanielade.pamietampsa.exception.auth.*;
import com.github.heisdanielade.pamietampsa.exception.media.FileSizeTooLargeException;
import com.github.heisdanielade.pamietampsa.exception.media.InvalidFileTypeException;
import com.github.heisdanielade.pamietampsa.exception.pet.PetAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleOtherExceptions(Exception ex) {
        System.out.println("\n============== (e) Error: " + ex.getMessage() + "\n");
        Map<String, Object>  body = new HashMap<>();
        body.put("error", "An internal error occured.");
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Account with provided email already exists in the system
    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleAccountAlreadyExists(AccountAlreadyExistsException ex){
        Map<String, Object>  body = new HashMap<>();
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    // Account with provided email does not exist in the system
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAccountNotFoundException(AccountNotFoundException ex){
        Map<String, Object>  body = new HashMap<>();
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("error", "Unauthorized");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    // Provided email or password is invalid
    @ExceptionHandler(InvalidLoginCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidLoginCredentialsException(InvalidLoginCredentialsException ex){
        Map<String, Object>  body = new HashMap<>();
        body.put("status", HttpStatus.UNAUTHORIZED.value()); 
        body.put("error", "Unauthorized");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    // Email attached to the Account is not verified, therefore, user isEnabled = false
    @ExceptionHandler(AccountNotVerifiedException.class)
    public ResponseEntity<Map<String, Object>> handleAccountNotVerifiedException(AccountNotVerifiedException ex){
        Map<String, Object>  body = new HashMap<>();
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("error", "Unauthorized");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    // Email attached to the Account is already verified; therefore, user isEnabled = true
    @ExceptionHandler(AccountAlreadyVerifiedException.class)
    public ResponseEntity<Map<String, Object>> handleAccountAlreadyVerifiedException(AccountAlreadyVerifiedException ex){
        Map<String, Object>  body = new HashMap<>();
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    // Provided OTP for email verification is invalid
    @ExceptionHandler(ExpiredVerificationCodeException.class)
    public ResponseEntity<Map<String, Object>> handleExpiredVerificationCodeException(ExpiredVerificationCodeException ex){
        Map<String, Object>  body = new HashMap<>();
        body.put("status", HttpStatus.GONE.value());
        body.put("error", "Gone");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.GONE);
    }

    // Provided OTP for email verification is expired
    @ExceptionHandler(InvalidVerificationCodeException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidVerificationCodeException(InvalidVerificationCodeException ex){
        Map<String, Object>  body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }



    // ===========================================================================

    // Pet for provided user already exists in the system
    @ExceptionHandler(PetAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handlePetAlreadyExists(PetAlreadyExistsException ex){
        Map<String, Object>  body = new HashMap<>();
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }



    // ===========================================================================

    // Uploaded File Type is not allowed
    @ExceptionHandler(InvalidFileTypeException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidFileType(InvalidFileTypeException ex){
        Map<String, Object>  body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Uploaded File Size exceeds the allowed limit
    @ExceptionHandler(FileSizeTooLargeException.class)
    public ResponseEntity<Map<String, Object>> handleFileSizeTooLarge(FileSizeTooLargeException ex){
        Map<String, Object>  body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


}

