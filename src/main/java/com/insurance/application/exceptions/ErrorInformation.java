package com.insurance.application.exceptions;

import java.time.LocalDateTime;

public class ErrorInformation {

    private final String message;
    private final LocalDateTime timeStamp;

    public ErrorInformation(String message, LocalDateTime timeStamp) {
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

}
