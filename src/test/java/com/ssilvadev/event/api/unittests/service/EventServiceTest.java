package com.ssilvadev.event.api.unittests.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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

import com.ssilvadev.event.api.dto.event.request.RequestInPersonEventDTO;
import com.ssilvadev.event.api.dto.event.request.RequestRemoteEventDTO;
import com.ssilvadev.event.api.dto.event.response.ResponseEventDTO;
import com.ssilvadev.event.api.exception.EventNotFound;
import com.ssilvadev.event.api.exception.RequiredNonNullObject;
import com.ssilvadev.event.api.mocks.MockEvent;
import com.ssilvadev.event.api.model.event.Event;
import com.ssilvadev.event.api.repository.event.EventRepository;
import com.ssilvadev.event.api.service.event.EventService;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository repository;

    @InjectMocks
    private EventService service;

    private static MockEvent mock;
    private static Event remoteEvent;
    private static Event inPersonEvent;

    private static RequestRemoteEventDTO remoteEventDto;
    private static RequestInPersonEventDTO inPersonEventDTO;

    @BeforeEach
    void setUp() {
        mock = new MockEvent();
        remoteEvent = mock.mockRemoteEventEntity();
        inPersonEvent = mock.mockInPersonEventEntity();

        remoteEventDto = mock.mockRemoteEventDTO();
        inPersonEventDTO = mock.mockInPersonEventDTO();
    }

    @Test
    void shouldCreateARemoteEvent() {
        when(repository.save(any(Event.class))).thenReturn(inPersonEvent);

        ResponseEventDTO response = service.createRemoteEvent(remoteEventDto);

        assertNotNull(response);

        assertEquals("Tech Event 2", response.title());
        assertEquals("https://www.techevent.com/event/2/details", response.eventUrl());
        assertEquals("https://www.techevent.com/2/eventbanner.png", response.eventImageUrl());
        assertEquals("Take part in the biggest technology 2º event of the year! There will be three days of talks.",
                response.description());
        assertEquals(Integer.valueOf(100), response.capacity());

        assertNull(response.address());

        var futureDate = LocalDate.of(2026, 04, 21)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        assertEquals(Date.from(futureDate), response.eventDate());
    }

    @Test
    void shouldCreateAInPersonEvent() {
        when(repository.save(any(Event.class))).thenReturn(inPersonEvent);

        ResponseEventDTO response = service.createInPersonEvent(inPersonEventDTO);

        assertNotNull(response);

        assertEquals("Tech Event", response.title());
        assertEquals("https://www.techevent.com/event/details", response.eventUrl());
        assertEquals("https://www.techevent.com/eventbanner.png", response.eventImageUrl());
        assertEquals("Take part in the biggest technology event of the year! There will be three days of talks.",
                response.description());
        assertEquals(Integer.valueOf(150), response.capacity());

        var futureDate = LocalDate.of(2026, 04, 21)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        assertEquals(Date.from(futureDate), response.eventDate());

        assertEquals("Av. Main 123", response.address().street());
        assertEquals("Neighborhood SP", response.address().neighborhood());
        assertEquals("São Paulo", response.address().city());
        assertEquals("SP", response.address().state());
        assertEquals("01223098", response.address().cep());
    }

    @Test
    void shouldGetInPersonEventById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(inPersonEvent));

        ResponseEventDTO response = service.findById(anyLong());

        assertNotNull(response);

        assertEquals("Tech Event", response.title());
        assertEquals("https://www.techevent.com/event/details", response.eventUrl());
        assertEquals("https://www.techevent.com/eventbanner.png", response.eventImageUrl());
        assertEquals("Take part in the biggest technology event of the year! There will be three days of talks.",
                response.description());
        assertEquals(Integer.valueOf(150), response.capacity());

        var futureDate = LocalDate.of(2026, 04, 21)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        assertEquals(Date.from(futureDate), response.eventDate());

        assertEquals("Av. Main 123", response.address().street());
        assertEquals("Neighborhood SP", response.address().neighborhood());
        assertEquals("São Paulo", response.address().city());
        assertEquals("SP", response.address().state());
        assertEquals("01223098", response.address().cep());
    }

    @Test
    void shouldThrowExceptionWhenCreateEventWithNullArgument() {
        Exception exceptionInPerson = assertThrows(RequiredNonNullObject.class,
                () -> service.createInPersonEvent(null));
        Exception exceptionRemote = assertThrows(RequiredNonNullObject.class,
                () -> service.createRemoteEvent(null));

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessageInPerson = exceptionInPerson.getMessage();
        String actualMessageRemote = exceptionRemote.getMessage();

        assertTrue(actualMessageInPerson.contains(expectedMessage));
        assertTrue(actualMessageRemote.contains(expectedMessage));
    }

    @Test
    void shouldGetRemoteEventById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(remoteEvent));

        ResponseEventDTO response = service.findById(1L);

        assertNotNull(response);

        assertEquals("Tech Event 2", response.title());
        assertEquals("https://www.techevent.com/event/2/details", response.eventUrl());
        assertEquals("https://www.techevent.com/2/eventbanner.png", response.eventImageUrl());
        assertEquals("Take part in the biggest technology 2º event of the year! There will be three days of talks.",
                response.description());
        assertEquals(Integer.valueOf(100), response.capacity());

        var futureDate = LocalDate.of(2026, 04, 21)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        assertEquals(Date.from(futureDate), response.eventDate());
    }

    @Test
    void shouldThrowExceptionWhenGetEventWithInvalidId() {
        Exception exception = assertThrows(EventNotFound.class, () -> service.findById(null));

        String expectedMessage = "Event not found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldDeleteEventById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(remoteEvent));

        service.delete(anyLong());

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Event.class));

        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldGetAllEvents() {
        var entities = mock.mockEventList();

        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(entities));

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "title"));

        var result = service.getAll(pageable);

        assertNotNull(result);
        assertTrue(result.getSize() > 0);
        assertTrue(result.getSize() == 10);

        var eventOne = result.getContent().get(0);

        assertNotNull(eventOne);
        assertNotNull(eventOne.id());
        assertTrue(eventOne.id() == 1);

        assertEquals("Tech Event 2", eventOne.title());
        assertEquals("https://www.techevent.com/event/2/details", eventOne.eventUrl());
        assertEquals("https://www.techevent.com/2/eventbanner.png", eventOne.eventImageUrl());
        assertEquals("Take part in the biggest technology 2º event of the year! There will be three days of talks.",
                eventOne.description());

        assertEquals(Integer.valueOf(100), eventOne.capacity());

        var futureDate = LocalDate.of(2026, 04, 21)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        assertEquals(Date.from(futureDate), eventOne.eventDate());

        var eventFour = result.getContent().get(3);

        assertNotNull(eventFour);
        assertNotNull(eventFour.id());
        assertTrue(eventFour.id() == 4);

        assertEquals("Tech Event 2", eventFour.title());
        assertEquals("https://www.techevent.com/event/2/details", eventFour.eventUrl());
        assertEquals("https://www.techevent.com/2/eventbanner.png", eventFour.eventImageUrl());
        assertEquals("Take part in the biggest technology 2º event of the year! There will be three days of talks.",
                eventFour.description());
        assertEquals(Integer.valueOf(100), eventFour.capacity());
        assertEquals(Date.from(futureDate), eventFour.eventDate());
    }
}
