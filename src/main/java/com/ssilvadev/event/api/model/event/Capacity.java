package com.ssilvadev.event.api.model.event;

import java.util.Objects;

import com.ssilvadev.event.api.model.common.Validatable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Capacity implements Validatable<Integer> {

    @Column(name = "capacity", nullable = true)
    private Integer capacity;

    public Capacity(Integer capacity) {
        validate(capacity);

        this.capacity = capacity;
    }

    @Override
    public void validate(Integer value) {
        if (value != null && value <= 0) {
            throw new IllegalArgumentException("The capacity must be greater than zero");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Capacity that = (Capacity) o;
        return Objects.equals(this.capacity, that.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(capacity);
    }

    public Integer getValue() {
        return this.capacity;
    }
}
