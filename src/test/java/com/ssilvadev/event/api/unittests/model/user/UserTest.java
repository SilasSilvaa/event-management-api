package com.ssilvadev.event.api.unittests.model.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ssilvadev.event.api.model.user.Email;
import com.ssilvadev.event.api.model.user.Gender;
import com.ssilvadev.event.api.model.user.LastName;
import com.ssilvadev.event.api.model.user.Name;
import com.ssilvadev.event.api.model.user.User;
import com.ssilvadev.event.api.unittests.model.mocks.MockUser;

class UserTest {

    private User user = new MockUser().mockUserEntity();

    @BeforeAll
    static void setUp() {

    }

    @Test
    void shouldCreateAUser() {

        assertNotNull(user);

        assertEquals("Wood", user.getName());
        assertEquals("Phethean", user.getLastName());
        assertEquals("wphethean0@ebay.com", user.getEmail());
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
}
