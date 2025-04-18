package com.ssilvadev.event.api.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(name = "name", nullable = false)
    private Name name;

    @Embedded
    @Column(name = "last_name", nullable = false)
    private LastName lastName;

    @Embedded
    @Column(name = "email", nullable = false)
    private Email email;

    @Embedded
    @Column(name = "gender", nullable = false)
    private Gender gender;

    public User(Name name, LastName lastName, Email email, Gender gender) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public LastName getLastName() {
        return lastName;
    }

    public Email getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

}
