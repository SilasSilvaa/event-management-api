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

import org.junit.jupiter.api.BeforeEach;
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

import com.ssilvadev.event.api.dto.user.request.RequestUserDTO;
import com.ssilvadev.event.api.dto.user.response.Gender;
import com.ssilvadev.event.api.dto.user.response.ResponseUserDTO;
import com.ssilvadev.event.api.exception.EmailAlreadyExists;
import com.ssilvadev.event.api.exception.RequiredNonNullObject;
import com.ssilvadev.event.api.exception.UserNotFound;
import com.ssilvadev.event.api.mocks.MockUser;
import com.ssilvadev.event.api.model.user.Email;
import com.ssilvadev.event.api.model.user.User;
import com.ssilvadev.event.api.repository.UserRepository;
import com.ssilvadev.event.api.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;
    private static User user;
    private static MockUser mockUser;
    private static RequestUserDTO dto;

    @BeforeEach
    void setUp() {
        mockUser = new MockUser();
        user = mockUser.mockUserEntity();
        dto = mockUser.mockUserDto();
    }

    @Test
    void shouldCreateAUser() {
        when(repository.save(any(User.class))).thenReturn(user);

        ResponseUserDTO result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.id());

        assertEquals("Wood", result.name());
        assertEquals("Phethean", result.lastName());
        assertEquals("wphethean0@ebay.com", result.email());
        assertEquals(Gender.MALE, result.gender());
    }

    @Test
    void shouldThrowExeceptionWhenCreateAUserWithExistEmail() {
        when(repository.existsByEmail(any(Email.class))).thenReturn(true);

        assertThrows(EmailAlreadyExists.class, () -> service.create(dto));
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
    void shouldUpdateAUser() {
        var dto = new RequestUserDTO(
                "Wood",
                "Phehean Robbings",
                "wphethean0@ebay.com",
                Gender.MALE);

        when(repository.findById(1L))
                .thenReturn(Optional.of(user));
        when(repository.save(any(User.class))).thenReturn(user);

        ResponseUserDTO result = service.update(1L, dto);

        assertNotNull(result);
        assertNotNull(result.id());

        assertEquals("Wood", result.name());
        assertEquals("Phehean Robbings", result.lastName());
        assertEquals("wphethean0@ebay.com", result.email());
        assertEquals(Gender.MALE, result.gender());
    }

    @Test
    void shouldDeleteUserById() {
        ReflectionTestUtils.setField(user, "id", 1L);

        when(repository.findById(anyLong())).thenReturn(Optional.of(user));

        service.delete(1L);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(User.class));

        verifyNoMoreInteractions(repository);
    }
}
