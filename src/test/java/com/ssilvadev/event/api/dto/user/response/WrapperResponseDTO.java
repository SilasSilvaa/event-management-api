package com.ssilvadev.event.api.dto.user.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WrapperResponseDTO(List<ResponseUserDTO> content) {
}
