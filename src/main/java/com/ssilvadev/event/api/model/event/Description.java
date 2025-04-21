package com.ssilvadev.event.api.model.event;

import java.util.Objects;

import com.ssilvadev.event.api.model.common.Validatable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Description implements Validatable<String> {

    @Column(name = "description", nullable = false)
    private String value;

    public Description(String value) {
        validate(value);

        this.value = value;
    }

    @Override
    public void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Description is required.");
        }

        if (value.length() > 600) {
            throw new IllegalArgumentException("Description cannot be longer than 600 characters.");
        }
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Description that = (Description) o;
        return Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
