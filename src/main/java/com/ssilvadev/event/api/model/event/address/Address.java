package com.ssilvadev.event.api.model.event.address;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class Address {

    @Embedded
    private Street street;

    @Embedded
    private Neighborhood neighborhood;

    @Embedded
    private City city;

    @Embedded
    private State state;

    @Embedded
    private CEP cep;

    public Address(
            Street street,
            Neighborhood neighborhood,
            City city,
            State state,
            CEP cep) {
        this.street = street;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.cep = cep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Address address = (Address) o;

        if (!Objects.equals(street, address.street))
            return false;
        if (!Objects.equals(neighborhood, address.neighborhood))
            return false;
        if (!Objects.equals(city, address.city))
            return false;
        if (!Objects.equals(state, address.state))
            return false;
        return Objects.equals(cep, address.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, neighborhood, city, state, cep);
    }

    public String getStreet() {
        return street.getValue();
    }

    public String getNeighborhood() {
        return neighborhood.getValue();
    }

    public String getCity() {
        return city.getValue();
    }

    public String getState() {
        return state.getValue();
    }

    public String getCep() {
        return cep.getValue();
    }

}
