package com.ssilvadev.event.api.mocks;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.test.util.ReflectionTestUtils;

import com.ssilvadev.event.api.dto.event.AddressDTO;
import com.ssilvadev.event.api.dto.event.request.RequestInPersonEventDTO;
import com.ssilvadev.event.api.dto.event.request.RequestRemoteEventDTO;
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

		ReflectionTestUtils.setField(event, "id", 1L);
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

		var capacity = new Capacity(100);

		Event event = Event.createRemoteEvent(
				title,
				description,
				eventDate,
				eventUrl,
				eventUrlImage,
				capacity);

		ReflectionTestUtils.setField(event, "id", 1L);
		return event;
	}

	public RequestRemoteEventDTO mockRemoteEventDTO() {
		var title = "Tech Event 2";
		var eventUrl = "https://www.techevent.com/event/2/details";
		var eventUrlImage = "https://www.techevent.com/2/eventbanner.png";
		var futureDate = LocalDate.of(2026, 04, 21)
				.atStartOfDay(ZoneId.systemDefault())
				.toInstant();

		var eventDate = Date.from(futureDate);
		var description = "Take part in the biggest technology 2º event of the year! There will be three days of talks.";

		Integer capacity = 100;

		return new RequestRemoteEventDTO(
				title,
				description,
				eventDate,
				eventUrl,
				eventUrlImage,
				capacity);

	}

	public RequestInPersonEventDTO mockInPersonEventDTO() {
		var title = "Tech Event";
		var eventUrl = "https://www.techevent.com/event/details";
		var eventUrlImage = "https://www.techevent.com/eventbanner.png";
		var futureDate = LocalDate.of(2026, 04, 21)
				.atStartOfDay(ZoneId.systemDefault())
				.toInstant();

		var eventDate = Date.from(futureDate);
		var description = "Take part in the biggest technology event of the year! There will be three days of talks.";

		Integer capacity = 150;

		var street = "Av. Main 123";
		var neighborhood = "Neighborhood SP";
		var city = "São Paulo";
		var state = "SP";
		var cep = "01223098";

		var address = new AddressDTO(street, neighborhood, city, state, cep);

		return new RequestInPersonEventDTO(
				title,
				description,
				eventDate,
				address,
				eventUrl,
				eventUrlImage,
				capacity);
	}

	public List<Event> mockEventList() {
		var entities = new ArrayList<Event>();

		for (int i = 1; i <= 10; i++) {

			var event = mockRemoteEventEntity();
			ReflectionTestUtils.setField(event, "id", Long.valueOf(i));

			entities.add(event);
		}

		return entities;
	}
}
