package com.ssilvadev.event.api.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
enum Gender {

    FEMALE('F'),
    MALE('M');

    @Column(name = "gender", nullable = false)
    private final Character value;

    private Gender(Character value) {
        this.value = value;
    }

    public Character getValue() {
        return this.value;
    }

}
