package com.ssilvadev.event.api.unittests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.util.ReflectionTestUtils;

import com.ssilvadev.event.api.dto.user.response.Gender;
import com.ssilvadev.event.api.dto.user.response.ResponseUserDTO;
import com.ssilvadev.event.api.exception.RequiredNonNullObject;
import com.ssilvadev.event.api.model.user.User;
import com.ssilvadev.event.api.repository.UserRepository;
import com.ssilvadev.event.api.service.UserService;
import com.ssilvadev.event.api.unittests.model.mocks.MockUser;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    void shouldCreateAUser() {
        var mockUser = new MockUser();
        var dto = mockUser.mockUserDto();

        when(repository.save(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            ReflectionTestUtils.setField(u, "id", 1L);
            return u;

        });

        ResponseUserDTO result = service.createUser(dto);

        assertNotNull(result);
        assertNotNull(result.id());

        assertEquals("Wood", result.name());
        assertEquals("Phethean", result.lastName());
        assertEquals("wphethean0@ebay.com", result.email());
        assertEquals(Gender.MALE, result.gender());
    }

    @Test
    void shouldThrowExceptionWhenCreateUserWithNullArgument() {
        Exception exception = assertThrows(RequiredNonNullObject.class, () -> service.createUser(null));

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldGetAllUsers() {
        var mockUser = new MockUser();
        List<User> list = mockUser.mockListEntity();

        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(list));

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "name"));

        Page<ResponseUserDTO> allUsers = service.getAllUsers(pageable);

        assertNotNull(allUsers);
        assertEquals(list.size(), allUsers.getSize());

        var userOne = allUsers.getContent().get(0);

        assertNotNull(userOne);
        assertNotNull(userOne.id());

        assertEquals(1, userOne.id());
        assertEquals("Wood", userOne.name());
        assertEquals("Phethean", userOne.lastName());
        assertEquals("wphethean0@ebay.com", userOne.email());
        assertEquals(Gender.MALE, userOne.gender());

        var userFour = allUsers.getContent().get(3);

        assertNotNull(userFour);
        assertNotNull(userFour.id());

        assertEquals(4, userFour.id());
        assertEquals("Wood", userFour.name());
        assertEquals("Phethean", userFour.lastName());
        assertEquals("wphethean0@ebay.com", userFour.email());
        assertEquals(Gender.MALE, userFour.gender());
    }
}
