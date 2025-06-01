package com.ssilvadev.event.api.unittests.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ssilvadev.event.api.mocks.MockEvent;
import com.ssilvadev.event.api.model.event.Capacity;
import com.ssilvadev.event.api.model.event.Description;
import com.ssilvadev.event.api.model.event.Event;
import com.ssilvadev.event.api.model.event.EventDate;
import com.ssilvadev.event.api.model.event.EventUrl;
import com.ssilvadev.event.api.model.event.Title;
import com.ssilvadev.event.api.model.event.address.CEP;
import com.ssilvadev.event.api.model.event.address.City;
import com.ssilvadev.event.api.model.event.address.Neighborhood;
import com.ssilvadev.event.api.model.event.address.State;
import com.ssilvadev.event.api.model.event.address.Street;

public class EventTest {
    private Event inPersonevent = new MockEvent().mockInPersonEventEntity();
    private Event remoteEvent = new MockEvent().mockRemoteEventEntity();

    @BeforeAll
    static void setUp() {

    }

    @Test
    void shouldCreateAInPersonEvent() {
        assertNotNull(inPersonevent);

        var futureDate = LocalDate.of(2026, 04, 21)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        var eventDate = new EventDate(Date.from(futureDate));

        assertEquals("Tech Event", inPersonevent.getTitle());
        assertEquals("https://www.techevent.com/event/details", inPersonevent.getEventUrl());
        assertEquals("https://www.techevent.com/eventbanner.png", inPersonevent.getImageUrl());
        assertEquals("Take part in the biggest technology event of the year! There will be three days of talks.",
                inPersonevent.getDescription());
        assertEquals(eventDate.getDate().getTime(), inPersonevent.getDate().getTime());
        assertEquals(150, inPersonevent.getCapacity());

        assertEquals("Av. Main 123", inPersonevent.getAddress().getStreet());
        assertEquals("Neighborhood SP", inPersonevent.getAddress().getNeighborhood());
        assertEquals("São Paulo", inPersonevent.getAddress().getCity());
        assertEquals("SP", inPersonevent.getAddress().getState());
        assertEquals("01223098", inPersonevent.getAddress().getCep());
        assertFalse(inPersonevent.isRemote());
    }

    @Test
    void shouldCreateARemoteEvent() {
        assertNotNull(remoteEvent);

        var futureDate = LocalDate.of(2026, 04, 21)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        var eventDate = new EventDate(Date.from(futureDate));

        assertEquals("Tech Event 2", remoteEvent.getTitle());
        assertEquals("https://www.techevent.com/event/2/details", remoteEvent.getEventUrl());
        assertEquals("https://www.techevent.com/2/eventbanner.png", remoteEvent.getImageUrl());
        assertEquals("Take part in the biggest technology 2º event of the year! There will be three days of talks.",
                remoteEvent.getDescription());

        assertEquals(eventDate.getDate().getTime(), remoteEvent.getDate().getTime());
        assertEquals(100, remoteEvent.getCapacity());

        assertTrue(remoteEvent.isRemote());
    }

    @Test
    void shouldThrowExceptionWhenTitleIsInvalid() {
        var eventTitle = "";
        var invalidTitle = "A".repeat(101);

        assertThrows(IllegalArgumentException.class, () -> new Title(null));
        assertThrows(IllegalArgumentException.class, () -> new Title(eventTitle));
        assertThrows(IllegalArgumentException.class, () -> new Title(invalidTitle));
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsInvalid() {
        var eventDescription = "";
        var invalidDescription = "A".repeat(601);

        assertThrows(IllegalArgumentException.class, () -> new Description(null));
        assertThrows(IllegalArgumentException.class, () -> new Description(eventDescription));
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    void shouldThrowExceptionWhenDateIsInvalid() {
        var pastDateLocalDate = LocalDate.of(2025, 04, 20)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();

        Date pastDate = Date.from(pastDateLocalDate);
        Date actualDate = new Date();

        assertThrows(IllegalArgumentException.class, () -> new EventDate(null));
        assertThrows(IllegalArgumentException.class, () -> new EventDate(pastDate));
        assertThrows(IllegalArgumentException.class, () -> new EventDate(actualDate));
    }

    @Test
    void shouldThrowExceptionWhenEventUrlIsInvalid() {
        var eventUrl = "";
        var invalidUrl = "A".repeat(151);

        assertThrows(IllegalArgumentException.class, () -> new EventUrl(null));
        assertThrows(IllegalArgumentException.class, () -> new EventUrl(eventUrl));
        assertThrows(IllegalArgumentException.class, () -> new EventUrl(invalidUrl));
    }

    @Test
    void shouldThrowExceptionWhenEventImageUrlIsInvalid() {
        var eventUrl = "";
        var invalidUrl = "A".repeat(151);

        assertThrows(IllegalArgumentException.class, () -> new EventUrl(null));
        assertThrows(IllegalArgumentException.class, () -> new EventUrl(eventUrl));
        assertThrows(IllegalArgumentException.class, () -> new EventUrl(invalidUrl));
    }

    @Test
    void shouldThrowExceptionWhenCapacityIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new Capacity(-1));
    }

    @Test
    void shouldThrowExceptionWhenAddressIsInvalid() {

        var invalidStreet = "A".repeat(101);
        var invalidNeigborhood = "A".repeat(31);
        var invalidCity = "A".repeat(21);
        var invalidState = "SSP";
        var invalidCEP = "0123423";

        assertThrows(IllegalArgumentException.class, () -> new Street(null));
        assertThrows(IllegalArgumentException.class, () -> new Street(""));
        assertThrows(IllegalArgumentException.class, () -> new Street(invalidStreet));

        assertThrows(IllegalArgumentException.class, () -> new Neighborhood(null));
        assertThrows(IllegalArgumentException.class, () -> new Neighborhood(""));
        assertThrows(IllegalArgumentException.class, () -> new Neighborhood(invalidNeigborhood));

        assertThrows(IllegalArgumentException.class, () -> new City(null));
        assertThrows(IllegalArgumentException.class, () -> new City(""));
        assertThrows(IllegalArgumentException.class, () -> new City(invalidCity));

        assertThrows(IllegalArgumentException.class, () -> new State(null));
        assertThrows(IllegalArgumentException.class, () -> new State(""));
        assertThrows(IllegalArgumentException.class, () -> new State(invalidState));

        assertThrows(IllegalArgumentException.class, () -> new CEP(null));
        assertThrows(IllegalArgumentException.class, () -> new CEP(invalidCEP));
    }
}
