package com.ssilvadev.event.api.unittests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
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
import com.ssilvadev.event.api.exception.UserNotFound;
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

    private static MockUser mockUser;

    @BeforeAll
    static void setUp() {
        mockUser = new MockUser();
    }

    @Test
    void shouldCreateAUser() {
        var dto = mockUser.mockUserDto();
        // var user = mockUser.mockUserEntity();

        when(repository.save(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            ReflectionTestUtils.setField(u, "id", 1L);
            return u;

        });

        ResponseUserDTO result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.id());

        assertEquals("Wood", result.name());
        assertEquals("Phethean", result.lastName());
        assertEquals("wphethean0@ebay.com", result.email());
        assertEquals(Gender.MALE, result.gender());
    }

    @Test
    void shouldThrowExceptionWhenCreateUserWithNullArgument() {
        Exception exception = assertThrows(RequiredNonNullObject.class, () -> service.create(null));

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldGetUserById() {
        var user = mockUser.mockUserEntity();

        when(repository.findById(anyLong())).thenReturn(Optional.of(user));

        ResponseUserDTO response = service.findById(1L);

        assertNotNull(response);

        assertEquals("Wood", response.name());
        assertEquals("Wood", response.name());
        assertEquals("Phethean", response.lastName());
        assertEquals("wphethean0@ebay.com", response.email());
        assertEquals(Gender.MALE, response.gender());
    }

    @Test
    void shouldThrowExceptionWhenGetUserWithInvalidId() {
        Exception exception = assertThrows(UserNotFound.class, () -> service.findById(null));

        String expectedMessage = "User not found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldGetAllUsers() {
        List<User> list = mockUser.mockListEntity();

        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(list));

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "name"));

        Page<ResponseUserDTO> allUsers = service.getAll(pageable);

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

    @Test
    void shouldDeleteUserById() {
        var user = mockUser.mockUserEntity();
        ReflectionTestUtils.setField(user, "id", 1L);

        when(repository.findById(anyLong())).thenReturn(Optional.of(user));

        service.delete(1L);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(User.class));

        verifyNoMoreInteractions(repository);
    }
}
