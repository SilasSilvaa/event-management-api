package com.ssilvadev.event.api.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Email {

    @Column(name = "email", nullable = false)
    private String value;

    public Email() {
    }

    public Email(String value) {
        validate(value);

        this.value = value;
    }

    private void validate(String value) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("E-mail is required");
        }

        if (!value.matches(regex)) {
            throw new IllegalArgumentException("E-mail is not valid");
        }
    }

    public String getValue() {
        return this.value;
    }
}
