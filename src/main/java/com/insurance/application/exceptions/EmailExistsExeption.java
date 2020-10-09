package com.insurance.application.exceptions;

public class EmailExistsExeption extends RuntimeException {
    public EmailExistsExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
