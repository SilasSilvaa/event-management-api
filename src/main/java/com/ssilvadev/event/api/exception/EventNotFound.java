package com.ssilvadev.event.api.exception;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EventNotFound extends RuntimeException {
    public EventNotFound(String message) {
        super(message);
    }

    public EventNotFound() {
        super("Event not found.");
    }
}
