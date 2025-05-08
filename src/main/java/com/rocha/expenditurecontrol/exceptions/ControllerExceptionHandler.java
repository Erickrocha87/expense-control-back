package com.rocha.expenditurecontrol.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import java.time.Instant;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UsernameOrPasswordInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<StandartError> handleUsernameOrPasswordInvalidException(UsernameOrPasswordInvalidException e){
        StandartError error = new StandartError(Instant.now(), e.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String,StandartError>> handleUserNotFoundException(UserNotFoundException e){
        StandartError error = new StandartError(Instant.now(), e.getMessage());
        Map<String, StandartError> errors = new HashMap<>();
        errors.put("Messasge: The user not found" + "Error: ", error);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<StandartError> handleSubscriptionNotFoundException(UserNotFoundException e){
        StandartError error = new StandartError(Instant.now(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
