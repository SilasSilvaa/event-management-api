package com.ssilvadev.event.api.model.event.address;

import java.util.Objects;

import com.ssilvadev.event.api.model.common.Validatable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Neighborhood implements Validatable<String> {

    @Column(name = "neighborhood", nullable = false)
    private String value;

    public Neighborhood(String value) {
        this.value = value;
    }

    @Override
    public void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Neigborhood is required.");
        }

        if (value.length() > 30) {
            throw new IllegalArgumentException("Neigborhood cannot be longer than 30 characters.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Neighborhood that = (Neighborhood) o;
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
