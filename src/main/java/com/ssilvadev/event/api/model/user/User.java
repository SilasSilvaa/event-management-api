package com.ssilvadev.event.api.model.user;

import com.ssilvadev.event.api.dto.user.request.RequestUserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    public User() {
    }

    public User(Name name, LastName lastName, Email email, Gender gender) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }

    public User updateProperties(RequestUserDTO dto) {
        this.name = new Name(dto.name());
        this.lastName = new LastName(dto.lastName());
        this.email = new Email(dto.email());
        this.gender = Gender.valueOf(dto.gender().name());

        return this;
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
