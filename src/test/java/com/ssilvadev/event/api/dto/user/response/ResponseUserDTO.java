package com.ssilvadev.event.api.dto.user.response;

public record ResponseUserDTO(
        Long id,
        String name,
        String lastName,
        String email,
        Gender gender) {
}
