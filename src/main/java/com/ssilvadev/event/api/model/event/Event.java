package com.ssilvadev.event.api.model.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.ssilvadev.event.api.model.event.address.Address;
import com.ssilvadev.event.api.model.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @ManyToMany
    @JoinTable(name = "event_user", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> eventSubscribers = new ArrayList<>();;

    @Column(name = "remote", nullable = false)
    private boolean remote;

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
            EventUrlImage eventImageUrl) {

        return new Event(title,
                description,
                date,
                null,
                eventUrl,
                eventImageUrl,
                null,
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
