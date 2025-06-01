package com.ssilvadev.event.api.model.event;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import com.ssilvadev.event.api.model.common.Validatable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EventDate implements Validatable<Date> {

    @Column(name = "event_date", nullable = false)
    private Date date;

    public EventDate() {

    }

    public EventDate(Date date) {
        validate(date);

        this.date = date;
    }

    @Override
    public void validate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date is required.");
        }

        LocalDate inputDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();

        if (inputDate.isBefore(today)) {
            throw new IllegalArgumentException("The date cannot be in the past.");
        }

        if (inputDate.equals(today)) {
            throw new IllegalArgumentException("The selected date must be after today.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EventDate that = (EventDate) o;
        return Objects.equals(this.date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    public Date getDate() {
        return this.date;
    }

}
