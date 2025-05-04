package com.ssilvadev.event.api.model.event.address;

import com.ssilvadev.event.api.model.common.Validatable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CEP implements Validatable<String> {

    @Column(name = "cep")
    private String value;

    public CEP(String value) {
        validate(value);

        this.value = value;
    }

    @Override
    public void validate(String value) {
        if (value == null) {
            throw new IllegalArgumentException("CEP is required");
        }

        String regex = "^\\d{8}$";
        if (!value.matches(regex)) {
            throw new IllegalArgumentException("Invalid CEP format. It should contain exactly 8 digits");
        }
    }

    public String getValue() {
        return value;
    }
}
