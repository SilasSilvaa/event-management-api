package com.ssilvadev.event.api.dto.event.request;

import java.util.Date;

public record RequestRemoteEventDTO(
        String title,
        String description,
        Date eventDate,
        String eventUrl,
        String eventImageUrl,
        Integer capacity) {

}
