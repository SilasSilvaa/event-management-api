package com.ssilvadev.event.api.model.event.address;

import java.util.Objects;

import com.ssilvadev.event.api.model.common.Validatable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Street implements Validatable<String> {

    @Column(name = "street", nullable = false)
    private String value;

    public Street(String value) {
        validate(value);

        this.value = value;
    }

    @Override
    public void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Street is required.");
        }

        if (value.length() > 100) {
            throw new IllegalArgumentException("Street cannot be longer than 100 characters.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Street that = (Street) o;
        return Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String getValue() {
        return value;
    }
}
