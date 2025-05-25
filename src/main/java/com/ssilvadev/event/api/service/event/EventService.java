package com.ssilvadev.event.api.service.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssilvadev.event.api.dto.event.request.RequestInPersonEventDTO;
import com.ssilvadev.event.api.dto.event.request.RequestRemoteEventDTO;
import com.ssilvadev.event.api.dto.event.response.ResponseEventDTO;
import com.ssilvadev.event.api.exception.EventNotFound;
import com.ssilvadev.event.api.exception.RequiredNonNullObject;
import com.ssilvadev.event.api.mapper.event.EventMapper;
import com.ssilvadev.event.api.model.event.Capacity;
import com.ssilvadev.event.api.model.event.Description;
import com.ssilvadev.event.api.model.event.Event;
import com.ssilvadev.event.api.model.event.EventDate;
import com.ssilvadev.event.api.model.event.EventUrl;
import com.ssilvadev.event.api.model.event.EventUrlImage;
import com.ssilvadev.event.api.model.event.Title;
import com.ssilvadev.event.api.model.event.address.Address;
import com.ssilvadev.event.api.model.event.address.CEP;
import com.ssilvadev.event.api.model.event.address.City;
import com.ssilvadev.event.api.model.event.address.Neighborhood;
import com.ssilvadev.event.api.model.event.address.State;
import com.ssilvadev.event.api.model.event.address.Street;
import com.ssilvadev.event.api.repository.event.EventRepository;
import com.ssilvadev.event.api.service.utils.ServiceUtils;

@Service
public class EventService {

    private final EventRepository repository;
    private static Logger logger = LoggerFactory.getLogger(EventService.class);
    private final static ServiceUtils serviceUtils = new ServiceUtils(EventService.class);

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public Page<ResponseEventDTO> getAll(Pageable pageable) {
        logger.info("Get all events");

        var events = repository.findAll(pageable);
        System.out.println(events);

        var response = events.stream().map(event -> EventMapper.entityToDTO(event)).toList();

        return new PageImpl<>(response, pageable, events.getTotalElements());
    }

    public ResponseEventDTO findById(Long id) {
        logger.info("Finding event by id {}", id);

        var entity = serviceUtils.getEntityOrThrow(repository, id, new EventNotFound());

        return EventMapper.entityToDTO(entity);
    }

    public ResponseEventDTO createRemoteEvent(RequestRemoteEventDTO dto) {
        if (dto == null) {
            throw new RequiredNonNullObject();
        }

        logger.info("Creating event");

        var title = new Title(dto.title());
        var description = new Description(dto.description());
        var date = new EventDate(dto.eventDate());
        var eventUrl = new EventUrl(dto.eventUrl());
        var eventImageUrl = new EventUrlImage(dto.eventImageUrl());
        var capacity = new Capacity(dto.capacity());

        Event remoteEvent = Event.createRemoteEvent(
                title,
                description,
                date,
                eventUrl,
                eventImageUrl,
                capacity);

        repository.save(remoteEvent);

        return EventMapper.entityToDTO(remoteEvent);
    }

    public ResponseEventDTO createInPersonEvent(RequestInPersonEventDTO dto) {
        if (dto == null) {
            throw new RequiredNonNullObject();
        }

        logger.info("Creating event");

        var title = new Title(dto.title());
        var description = new Description(dto.description());
        var date = new EventDate(dto.eventDate());
        var eventUrl = new EventUrl(dto.eventUrl());
        var eventImageUrl = new EventUrlImage(dto.eventImageUrl());
        var capacity = new Capacity(dto.capacity());
        var address = getAddress(dto);

        Event inPersonEvent = Event.createInPersonEvent(
                title,
                description,
                date,
                address,
                eventUrl,
                eventImageUrl,
                capacity);

        repository.save(inPersonEvent);

        return EventMapper.entityToDTO(inPersonEvent);
    }

    public void delete(Long id) {
        logger.info("Deleting event by id {}", id);

        var entity = serviceUtils.getEntityOrThrow(repository, id, new EventNotFound());

        repository.delete(entity);
    }

    private Address getAddress(RequestInPersonEventDTO dto) {
        var street = new Street(dto.address().street());
        var neighborhood = new Neighborhood(dto.address().neighborhood());
        var city = new City(dto.address().city());
        var state = new State(dto.address().state());
        var cep = new CEP(dto.address().cep());

        return new Address(
                street,
                neighborhood,
                city,
                state,
                cep);
    }
}
