package com.insurance.application.exceptions.exceptionclasses;

public class EmailExistsExeption extends RuntimeException {
    public EmailExistsExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
