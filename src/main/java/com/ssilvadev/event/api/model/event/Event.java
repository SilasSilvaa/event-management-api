package com.ssilvadev.event.api.model.event;

import java.util.Date;
import java.util.Objects;

import com.ssilvadev.event.api.model.event.address.Address;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Title title;

    @Embedded
    private Description description;

    @Embedded
    private EventDate eventDate;

    @Embedded
    private Address address;

    @Embedded
    private EventUrl eventUrl;

    @Embedded
    private EventUrlImage eventImageUrl;

    @Embedded
    private Capacity capacity;

    @Column(name = "remote", nullable = false)
    private boolean remote;

    public Event() {

    }

    private Event(
            Title title,
            Description description,
            EventDate eventDate,
            Address address,
            EventUrl eventUrl,
            EventUrlImage eventImageUrl,
            Capacity capacity,
            boolean remote) {

        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.address = address;
        this.eventUrl = eventUrl;
        this.eventImageUrl = eventImageUrl;
        this.capacity = capacity;
        this.remote = remote;
    }

    public static Event createInPersonEvent(
            Title title,
            Description description,
            EventDate eventDate,
            Address address,
            EventUrl eventUrl,
            EventUrlImage eventImageUrl,
            Capacity capacity) {

        if (capacity == null) {
            throw new IllegalArgumentException("Capacity is required for in person event");
        }

        return new Event(
                title,
                description,
                eventDate,
                address,
                eventUrl,
                eventImageUrl,
                capacity,
                false);
    }

    public static Event createRemoteEvent(
            Title title,
            Description description,
            EventDate date,
            EventUrl eventUrl,
            EventUrlImage eventImageUrl,
            Capacity capacity) {

        return new Event(title,
                description,
                date,
                null,
                eventUrl,
                eventImageUrl,
                capacity,
                true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Event that = (Event) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title.getValue();
    }

    public String getDescription() {
        return description.getValue();
    }

    public Date getDate() {
        return eventDate.getDate();
    }

    public Address getAddress() {
        return address;
    }

    public String getEventUrl() {
        return eventUrl.getValue();
    }

    public String getImageUrl() {
        return eventImageUrl.getValue();
    }

    public Integer getCapacity() {
        return capacity != null ? capacity.getValue() : null;
    }

    public boolean isRemote() {
        return remote;
    }
}
