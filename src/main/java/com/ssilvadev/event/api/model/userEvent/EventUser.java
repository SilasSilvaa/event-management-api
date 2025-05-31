package com.ssilvadev.event.api.model.userEvent;

import java.util.Objects;

import com.ssilvadev.event.api.model.event.Event;
import com.ssilvadev.event.api.model.user.User;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "event_user")
public class EventUser {

    @EmbeddedId
    private EventUserId id;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    public EventUser() {

    }

    public EventUser(Event event, User user) {
        this.id = new EventUserId(event.getId(), user.getId());
        this.event = event;
        this.user = user;
    }

    public EventUserId getId() {
        return id;
    }

    public Event getEvent() {
        return event;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EventUser that = (EventUser) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
