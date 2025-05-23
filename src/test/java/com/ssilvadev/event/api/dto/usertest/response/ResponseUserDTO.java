package com.ssilvadev.event.api.dto.usertest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssilvadev.event.api.dto.user.response.Gender;

public record ResponseUserDTO(
        Long id,
        String name,
        @JsonProperty(value = "last_name") String lastName,
        String email,
        Gender gender) {
}
