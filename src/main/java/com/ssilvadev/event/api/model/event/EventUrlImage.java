package com.ssilvadev.event.api.model.event;

import java.util.Objects;

import com.ssilvadev.event.api.model.common.Validatable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EventUrlImage implements Validatable<String> {

    @Column(name = "event_url_image", nullable = false)
    private String value;

    public EventUrlImage(String value) {
        validate(value);

        this.value = value;
    }

    @Override
    public void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Event image Url is required.");
        }

        if (value.length() > 150) {
            throw new IllegalArgumentException("Event image Url cannot be longer than 150 characters.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EventUrlImage that = (EventUrlImage) o;
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
