package com.ssilvadev.event.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyRegistred extends RuntimeException {
    public UserAlreadyRegistred(String message) {
        super(message);
    }

    public UserAlreadyRegistred() {
        super("User already registred at the event");
    }
}
