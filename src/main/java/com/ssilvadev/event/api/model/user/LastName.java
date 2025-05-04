package com.ssilvadev.event.api.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class LastName {

    @Column(name = "last_name", nullable = false)
    private String value;

    public LastName() {
    }

    public LastName(String value) {
        validate(value);

        this.value = value;
    }

    private void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Last name is required.");
        }

        if (value.length() > 100) {
            throw new IllegalArgumentException("Last Name cannot be longer than 100 characters.");
        }
    }

    public String getValue() {
        return this.value;
    }
}
