package com.ssilvadev.event.api.model.common;

public interface Validatable<T> {

    void validate(T value);
}
