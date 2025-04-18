package com.ssilvadev.event.api.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
class Email {

    @Column(name = "email", nullable = false)
    private String value;

    public Email(String value) {
        validate(value);

        this.value = value;
    }

    private void validate(String value) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        if (value.isEmpty() || value == null) {
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
