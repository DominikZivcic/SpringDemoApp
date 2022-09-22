package com.demo.demoApp.Exception;

import java.time.ZonedDateTime;

public class UserException {

    private final String message;
    private final ZonedDateTime timestamp;

    public UserException(String message, ZonedDateTime timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
