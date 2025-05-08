package com.ssilvadev.event.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotFound extends RuntimeException{
    public UserNotFound(String message) {
        super(message);
    }

    public UserNotFound() {
        super("User not found.");
    }
}
