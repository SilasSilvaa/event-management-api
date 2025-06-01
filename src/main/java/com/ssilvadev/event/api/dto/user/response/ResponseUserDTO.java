package com.ssilvadev.event.api.dto.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseUserDTO(
        Long id,
        String name,
        @JsonProperty("last_name") String lastName,
        String email,
        Gender gender) {
}
