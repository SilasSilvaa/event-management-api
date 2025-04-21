package com.ssilvadev.event.api.unittests.model.mocks;

import java.util.ArrayList;
import java.util.List;

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
}
