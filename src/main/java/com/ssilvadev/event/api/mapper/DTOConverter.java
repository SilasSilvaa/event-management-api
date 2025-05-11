package com.ssilvadev.event.api.mapper;

import com.ssilvadev.event.api.dto.user.response.Gender;
import com.ssilvadev.event.api.dto.user.response.ResponseUserDTO;
import com.ssilvadev.event.api.model.user.Email;
import com.ssilvadev.event.api.model.user.LastName;
import com.ssilvadev.event.api.model.user.Name;
import com.ssilvadev.event.api.model.user.User;

public class DTOConverter {

    public static ResponseUserDTO userToResponseDTO(User user) {
        return new ResponseUserDTO(user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                Gender.valueOf(user.getGender()));

    }

    public static User responseDTOToUser(ResponseUserDTO user) {
        var name = new Name(user.name());
        var lastName = new LastName(user.lastName());
        var email = new Email(user.email());
        var gender = com.ssilvadev.event.api.model.user.Gender.valueOf(user.gender().name());
        return new User(
                name,
                lastName,
                email,
                gender);
    }
}
