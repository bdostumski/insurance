package com.insurance.application.exceptions.exceptionclasses;

public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException(String message) {
        super(message);
    }
}
