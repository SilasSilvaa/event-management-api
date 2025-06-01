package com.ssilvadev.event.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SubscriptionNotFound extends RuntimeException {
    public SubscriptionNotFound(String message) {
        super(message);
    }

    public SubscriptionNotFound() {
        super("Subscription not Found.");
    }
}
