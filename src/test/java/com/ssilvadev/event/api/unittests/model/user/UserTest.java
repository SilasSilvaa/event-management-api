package com.ssilvadev.event.api.unittests.model.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.ssilvadev.event.api.model.user.Email;
import com.ssilvadev.event.api.model.user.Gender;
import com.ssilvadev.event.api.model.user.LastName;
import com.ssilvadev.event.api.model.user.Name;
import com.ssilvadev.event.api.model.user.User;

class UserTest {

    @Test
    void shouldCreateAUser() {

        var user = mockUser();

        assertNotNull(user);

        assertEquals("Wood", user.getName().getValue());
        assertEquals("Phethean", user.getLastName().getValue());
        assertEquals("wphethean0@ebay.com", user.getEmail().getValue());
        assertEquals(Gender.MALE, user.getGender());
    }

    @Test
    void shouldThrowExceptionWhenNameIsInvalid() {
        var invalidName = "A".repeat(101);

        assertThrows(IllegalArgumentException.class, () -> new Name(null));
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    void shouldThrowExceptionWhenLastNameIsInvalid() {
        var invalidLastName = "B".repeat(101);

        assertThrows(IllegalArgumentException.class, () -> new LastName(null));
        assertThrows(IllegalArgumentException.class, () -> new LastName(invalidLastName));

    }

    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {

        var invalidEmail = "wphethean0@";

        assertThrows(IllegalArgumentException.class, () -> new Email(null));
        assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }

    private User mockUser() {
        var name = new Name("Wood");
        var lastName = new LastName("Phethean");
        var email = new Email("wphethean0@ebay.com");
        var gender = Gender.MALE;

        User user = new User(name, lastName, email, gender);

        return user;
    }
}
