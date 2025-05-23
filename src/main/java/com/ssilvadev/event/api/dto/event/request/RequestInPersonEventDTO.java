package com.ssilvadev.event.api.dto.event.request;

import java.util.Date;

import com.ssilvadev.event.api.dto.event.AddressDTO;

public record RequestInPersonEventDTO(
        String title,
        String description,
        Date eventDate,
        AddressDTO address,
        String eventUrl,
        String eventImageUrl,
        Integer capacity) {

}
