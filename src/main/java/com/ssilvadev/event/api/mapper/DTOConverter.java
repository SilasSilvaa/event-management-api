package com.ssilvadev.event.api.mapper;

import com.ssilvadev.event.api.dto.user.response.Gender;
import com.ssilvadev.event.api.dto.user.response.ResponseUserDTO;
import com.ssilvadev.event.api.model.user.User;

public class DTOConverter {

    public static ResponseUserDTO userToResponseDTO(User user) {

        var dto = new ResponseUserDTO(user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                Gender.valueOf(user.getGender()));

        return dto;
    }
}
