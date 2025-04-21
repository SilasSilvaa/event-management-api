package com.ssilvadev.event.api.model.event;

import java.util.Objects;

import com.ssilvadev.event.api.model.common.Validatable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EventUrl implements Validatable<String> {

    @Column(name = "event_url", nullable = false)
    private String value;

    public EventUrl(String value) {
        validate(value);

        this.value = value;
    }

    @Override
    public void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Event Url is required.");
        }

        if (value.length() > 150) {
            throw new IllegalArgumentException("Event Url cannot be longer than 150 characters.");
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
        EventUrl that = (EventUrl) o;
        return Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
