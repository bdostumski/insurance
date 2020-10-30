package com.insurance.application.exceptions.exceptionclasses;

public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException(String message) {
        super(message);
    }
}

