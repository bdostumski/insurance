package com.insurance.application.exceptions.exceptionclasses;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
