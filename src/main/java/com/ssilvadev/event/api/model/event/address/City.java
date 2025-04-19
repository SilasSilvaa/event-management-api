package com.ssilvadev.event.api.model.event.address;

import java.util.Objects;

import com.ssilvadev.event.api.model.common.Validatable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class City implements Validatable<String> {

    @Column(name = "city", nullable = false)
    private String value;

    public City(String value) {
        this.value = value;
    }

    @Override
    public void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("State is required.");
        }

        if (value.length() > 20) {
            throw new IllegalArgumentException("State cannot be longer than 20 characters.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        City that = (City) o;
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
