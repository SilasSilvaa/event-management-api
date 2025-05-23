package com.ssilvadev.event.api.mapper.event;

import com.ssilvadev.event.api.dto.event.AddressDTO;
import com.ssilvadev.event.api.dto.event.response.ResponseEventDTO;
import com.ssilvadev.event.api.model.event.Event;

public class EventMapper {
    public static ResponseEventDTO entityToDTO(Event event) {
        var address = getAddress(event);

        return new ResponseEventDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                address,
                event.getEventUrl(),
                event.getImageUrl(),
                event.getCapacity(),
                event.isRemote());
    }

    private static AddressDTO getAddress(Event event) {
        var address = event.getAddress();

        if (address == null) {
            return null;
        }

        return new AddressDTO(
                address.getStreet(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getCep());
    }
}
