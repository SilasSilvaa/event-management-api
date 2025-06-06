package com.ssilvadev.event.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredNonNullObject extends RuntimeException {
    public RequiredNonNullObject(String message) {
        super(message);
    }

    public RequiredNonNullObject() {
        super("It is not allowed to persist a null object!");
    }
}
