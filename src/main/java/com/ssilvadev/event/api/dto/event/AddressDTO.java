package com.ssilvadev.event.api.dto.event;

public record AddressDTO(
        String street,
        String neighborhood,
        String city,
        String state,
        String cep) {

}
