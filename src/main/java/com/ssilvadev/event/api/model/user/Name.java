package com.ssilvadev.event.api.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
class Name {

    @Column(name = "name", nullable = false)
    private String value;

    public Name(String value) {
        validate(value);

        this.value = value;
    }

    private void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Name is required.");
        }

        if (value.length() > 100) {
            throw new IllegalArgumentException("Name cannot be longer than 100 characters.");
        }
    }

    public String getValue() {
        return this.value;
    }
}
