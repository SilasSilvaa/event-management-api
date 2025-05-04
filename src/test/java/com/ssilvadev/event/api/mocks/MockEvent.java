package com.ssilvadev.event.api.mocks;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.ssilvadev.event.api.model.event.Capacity;
import com.ssilvadev.event.api.model.event.Description;
import com.ssilvadev.event.api.model.event.Event;
import com.ssilvadev.event.api.model.event.EventDate;
import com.ssilvadev.event.api.model.event.EventUrl;
import com.ssilvadev.event.api.model.event.EventUrlImage;
import com.ssilvadev.event.api.model.event.Title;
import com.ssilvadev.event.api.model.event.address.Address;
import com.ssilvadev.event.api.model.event.address.CEP;
import com.ssilvadev.event.api.model.event.address.City;
import com.ssilvadev.event.api.model.event.address.Neighborhood;
import com.ssilvadev.event.api.model.event.address.State;
import com.ssilvadev.event.api.model.event.address.Street;

public class MockEvent {

    public Event mockInPersonEventEntity() {
        var title = new Title("Tech Event");
        var eventUrl = new EventUrl("https://www.techevent.com/event/details");
        var eventUrlImage = new EventUrlImage("https://www.techevent.com/eventbanner.png");
        var futureDate = LocalDate.of(2026, 04, 21)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();

        var eventDate = new EventDate(Date.from(futureDate));
        var description = new Description(
                "Take part in the biggest technology event of the year! There will be three days of talks.");
        var capacity = new Capacity(150);

        var street = new Street("Av. Main 123");
        var neighborhood = new Neighborhood("Neighborhood SP");
        var city = new City("São Paulo");
        var state = new State("SP");
        var cep = new CEP("01223098");

        var address = new Address(street, neighborhood, city, state, cep);

        var event = Event.createInPersonEvent(
                title,
                description,
                eventDate,
                address,
                eventUrl,
                eventUrlImage,
                capacity);

        return event;
    }

    public Event mockRemoteEventEntity() {
        var title = new Title("Tech Event 2");
        var eventUrl = new EventUrl("https://www.techevent.com/event/2/details");
        var eventUrlImage = new EventUrlImage("https://www.techevent.com/2/eventbanner.png");
        var futureDate = LocalDate.of(2026, 04, 21)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();

        var eventDate = new EventDate(Date.from(futureDate));
        var description = new Description(
                "Take part in the biggest technology 2º event of the year! There will be three days of talks.");

        var event = Event.createRemoteEvent(
                title,
                description,
                eventDate,
                eventUrl,
                eventUrlImage);

        return event;
    }
}
