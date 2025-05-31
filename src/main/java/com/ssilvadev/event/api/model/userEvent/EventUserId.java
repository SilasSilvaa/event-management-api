package com.ssilvadev.event.api.model.userEvent;

import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class EventUserId {

    private Long eventId;
    private Long userId;

    public EventUserId() {

    }

    public EventUserId(Long eventId, Long userId) {
        this.eventId = eventId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        EventUserId that = (EventUserId) o;

        return Objects.equals(eventId, that.eventId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, userId);
    }
}
