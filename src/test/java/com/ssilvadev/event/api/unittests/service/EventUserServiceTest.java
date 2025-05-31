package com.ssilvadev.event.api.unittests.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.ssilvadev.event.api.dto.user.response.Gender;
import com.ssilvadev.event.api.exception.EventNotFound;
import com.ssilvadev.event.api.exception.SubscriptionNotFound;
import com.ssilvadev.event.api.exception.UserAlreadyRegistred;
import com.ssilvadev.event.api.exception.UserNotFound;
import com.ssilvadev.event.api.mocks.MockEvent;
import com.ssilvadev.event.api.mocks.MockEventUser;
import com.ssilvadev.event.api.mocks.MockUser;
import com.ssilvadev.event.api.model.event.Event;
import com.ssilvadev.event.api.model.user.User;
import com.ssilvadev.event.api.model.userEvent.EventUser;
import com.ssilvadev.event.api.repository.event.EventRepository;
import com.ssilvadev.event.api.repository.user.UserRepository;
import com.ssilvadev.event.api.repository.userEvent.EventUserRepository;
import com.ssilvadev.event.api.service.eventUser.EventUserService;

@ExtendWith(MockitoExtension.class)
public class EventUserServiceTest {

    @Mock
    private EventUserRepository repository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EventUserService service;

    private static MockEvent mockEvent;
    private static MockUser mockUser;
    private static MockEventUser mockEventUser;

    private static User user;
    private static Event event;
    private static List<Event> events;
    private static List<User> users;
    private static EventUser eventUser;

    @BeforeEach
    void setUp() {
        mockEvent = new MockEvent();
        mockUser = new MockUser();
        mockEventUser = new MockEventUser();

        user = mockUser.mockUserEntity();
        event = mockEvent.mockRemoteEventEntity();
        events = mockEvent.mockEventList();
        users = mockUser.mockListEntity();
        eventUser = mockEventUser.mockEventUser();
    }

    @Test
    void shouldGetEventSubscribers() {
        when(eventRepository.findById(eq(1L))).thenReturn(Optional.of(event));
        when(repository.findByEventId(eq(1L), any(Pageable.class)))
                .thenReturn(new PageImpl<>(users));

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "user"));
        var results = service.getEventSubscribers(1L, pageable);

        assertNotNull(results);

        var userOne = results.getContent().get(0);

        assertNotNull(userOne.id());
        assertTrue(userOne.id() > 0);
        assertEquals("Wood", userOne.name());
        assertEquals("Phethean", userOne.lastName());
        assertEquals("wphethean0@ebay.com", userOne.email());
        assertEquals(Gender.MALE, userOne.gender());
    }

    @Test
    void shouldThrowExceptionWhenGetEventSubscribersWithInvalidId() {
        var exception = assertThrows(EventNotFound.class, () -> service.getEventSubscribers(1L, any(Pageable.class)));

        String expectedMessage = "Event not found.";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shoulGetEventRegistred() {
        when(userRepository.findById(eq(1L))).thenReturn(Optional.of(user));
        when(repository.findByUserId(eq(1L), any(Pageable.class)))
                .thenReturn(new PageImpl<>(events));

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "user"));
        var results = service.getEventsRegistred(1L, pageable);

        assertNotNull(results);

        var eventOne = results.getContent().get(0);

        assertNotNull(eventOne.id());
        assertTrue(eventOne.id() > 0);
        assertEquals("Tech Event 2", eventOne.title());
        assertEquals("https://www.techevent.com/event/2/details", eventOne.eventUrl());
        assertEquals("https://www.techevent.com/2/eventbanner.png", eventOne.eventImageUrl());
        assertEquals("Take part in the biggest technology 2º event of the year! There will be three days of talks.",
                eventOne.description());
        assertEquals(Integer.valueOf(100), eventOne.capacity());

        assertNull(eventOne.address());

        assertNotNull(eventOne.eventDate());
    }

    @Test
    void shouldThrowExceptionWhenGetEventRegistredWithInvalidId() {
        var exception = assertThrows(UserNotFound.class, () -> service.getEventsRegistred(1L, any(Pageable.class)));

        String expectedMessage = "User not found.";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldRegisterOnEvent() {
        when(eventRepository.findById(eq(1L))).thenReturn(Optional.of(event));
        when(userRepository.findById(eq(1L))).thenReturn(Optional.of(user));
        when(repository.existsByEventIdAndUserId(eq(1L), eq(1L))).thenReturn(false);
        when(repository.save(any(EventUser.class))).thenReturn(eventUser);

        var result = service.registerOnEvent(1L, 1L);

        assertNotNull(result);

        assertNotNull(result.id());
        assertTrue(result.id() > 0);
        assertEquals("Tech Event 2", result.title());
        assertEquals("https://www.techevent.com/event/2/details", result.eventUrl());
        assertEquals("https://www.techevent.com/2/eventbanner.png", result.eventImageUrl());
        assertEquals("Take part in the biggest technology 2º event of the year! There will be three days of talks.",
                result.description());
        assertEquals(Integer.valueOf(100), result.capacity());

        assertNull(result.address());

        assertNotNull(result.eventDate());
    }

    @Test
    void shouldThrowExceptionWhenRegisterOnEventWithInvalidArguments() {
        when(eventRepository.findById(eq(1L))).thenReturn(Optional.of(event));
        when(userRepository.findById(eq(1L))).thenReturn(Optional.of(user));
        when(repository.existsByEventIdAndUserId(eq(1L), eq(1L))).thenReturn(true);

        var exception = assertThrows(UserAlreadyRegistred.class, () -> service.registerOnEvent(1L, 1L));

        var expectedMessage = "User already registred at the event";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldCancelEventSubscription() {
        when(eventRepository.findById(eq(1L))).thenReturn(Optional.of(event));
        when(userRepository.findById(eq(1L))).thenReturn(Optional.of(user));
        when(repository.findByEventIdAndUserId(eq(1L), eq(1L))).thenReturn(Optional.of(eventUser));

        service.cancelEventSubscription(1L, 1L);

        verify(repository, times(1)).findByEventIdAndUserId(eq(1L), eq(1L));
        verify(repository, times(1)).delete(eventUser);

        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowExceptionWhenCancelEventSubscriptionWithInvalidArguments() {
        when(eventRepository.findById(eq(1L))).thenReturn(Optional.of(event));
        when(userRepository.findById(eq(1L))).thenReturn(Optional.of(user));
        when(repository.findByEventIdAndUserId(eq(1L), eq(1L))).thenReturn(Optional.empty());

        var exception = assertThrows(SubscriptionNotFound.class, () -> service.cancelEventSubscription(1L, 1L));

        var expectedMessage = "Subscription not Found.";

        assertEquals(expectedMessage, exception.getMessage());
    }
}
