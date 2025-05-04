package com.ssilvadev.event.api.dto.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssilvadev.event.api.dto.user.response.Gender;

public record RequestUserDTO(
        String name,
        @JsonProperty("last_name") String lastName,
        String email,
        Gender gender) {
}
