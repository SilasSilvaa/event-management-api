package com.ssilvadev.event.api.mocks;

import java.util.ArrayList;
import java.util.List;

import org.springframework.test.util.ReflectionTestUtils;

import com.ssilvadev.event.api.dto.user.request.RequestUserDTO;
import com.ssilvadev.event.api.model.user.Email;
import com.ssilvadev.event.api.model.user.Gender;
import com.ssilvadev.event.api.model.user.LastName;
import com.ssilvadev.event.api.model.user.Name;
import com.ssilvadev.event.api.model.user.User;

public class MockUser {
    public User mockUserEntity() {
        var name = new Name("Wood");
        var lastName = new LastName("Phethean");
        var email = new Email("wphethean0@ebay.com");
        var gender = Gender.MALE;

        User user = new User(name, lastName, email, gender);

        return user;
    }

    public RequestUserDTO mockUserDto() {
        var dto = new RequestUserDTO(
                "Wood",
                "Phethean",
                "wphethean0@ebay.com",
                com.ssilvadev.event.api.dto.user.response.Gender.MALE);

        return dto;
    }

    public List<User> mockListEntity() {
        List<User> entities = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {

            User user = mockUserEntity();
            ReflectionTestUtils.setField(user, "id", Long.valueOf(i));

            entities.add(user);
        }

        return entities;
    }
}
