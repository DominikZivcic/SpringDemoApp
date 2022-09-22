package com.demo.demoApp.Exception;

import java.time.ZonedDateTime;

public class AccountException {

    private final String message;
    private final ZonedDateTime timestamp;

    public AccountException(String message, ZonedDateTime timestamp) {
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
