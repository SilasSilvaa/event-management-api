package com.ssilvadev.event.api.dto.usertest.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ssilvadev.event.api.dto.user.response.ResponseUserDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WrapperResponseDTO(List<ResponseUserDTO> content) {
}
