package com.ssilvadev.event.api.model.user;

import java.util.ArrayList;
import java.util.List;

import com.ssilvadev.event.api.model.event.Event;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Name name;

    @Embedded
    private LastName lastName;

    @Embedded
    private Email email;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @ManyToMany(mappedBy = "eventSubscribers")
    private List<Event> registeredEvents = new ArrayList<>();

    public User() {
    }

    public User(Name name, LastName lastName, Email email, Gender gender) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public String getLastName() {
        return lastName.getValue();
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getGender() {
        return gender.name();
    }

    public List<Event> getRegisteredEvents() {
        return registeredEvents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;

        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
