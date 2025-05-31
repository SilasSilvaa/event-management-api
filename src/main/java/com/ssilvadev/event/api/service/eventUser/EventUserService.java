package com.ssilvadev.event.api.service.eventUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssilvadev.event.api.dto.event.response.ResponseEventDTO;
import com.ssilvadev.event.api.dto.user.response.ResponseUserDTO;
import com.ssilvadev.event.api.exception.EventNotFound;
import com.ssilvadev.event.api.exception.SubscriptionNotFound;
import com.ssilvadev.event.api.exception.UserAlreadyRegistred;
import com.ssilvadev.event.api.exception.UserNotFound;
import com.ssilvadev.event.api.mapper.DTOConverter;
import com.ssilvadev.event.api.mapper.event.EventMapper;
import com.ssilvadev.event.api.model.userEvent.EventUser;
import com.ssilvadev.event.api.repository.event.EventRepository;
import com.ssilvadev.event.api.repository.user.UserRepository;
import com.ssilvadev.event.api.repository.userEvent.EventUserRepository;
import com.ssilvadev.event.api.service.utils.ServiceUtils;

@Service
public class EventUserService {

    private final EventUserRepository repository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    private final static ServiceUtils serviceUtils = new ServiceUtils(EventUserService.class);
    private final static Logger logger = LoggerFactory.getLogger(EventUserService.class);

    public EventUserService(
            EventUserRepository repository,
            EventRepository eventRepository,
            UserRepository userRepository) {
        this.repository = repository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public Page<ResponseUserDTO> getEventSubscribers(Long eventId, Pageable pageable) {
        var event = serviceUtils.getEntityOrThrow(eventRepository, eventId, new EventNotFound());

        logger.info("Getting subscribers");
        var eventUser = repository.findByEventId(event.getId(), pageable);

        var list = eventUser.stream()
                .map(e -> DTOConverter.userToResponseDTO(e))
                .toList();

        return new PageImpl<>(list, pageable, eventUser.getTotalElements());
    }

    public Page<ResponseEventDTO> getEventsRegistred(Long userId, Pageable pageable) {
        var user = serviceUtils.getEntityOrThrow(userRepository, userId, new UserNotFound());

        logger.info("Getting events");
        var eventUser = repository.findByUserId(user.getId(), pageable);

        var list = eventUser.stream()
                .map(e -> EventMapper.entityToDTO(e))
                .toList();

        return new PageImpl<>(list, pageable, eventUser.getTotalElements());
    }

    public ResponseEventDTO registerOnEvent(Long userId, Long eventId) {
        logger.info("Registering a user for an event");

        var user = serviceUtils.getEntityOrThrow(userRepository, userId, new UserNotFound());
        var event = serviceUtils.getEntityOrThrow(eventRepository, eventId, new EventNotFound());

        boolean userAlreadyRegistred = repository.existsByEventIdAndUserId(eventId, userId);

        if (userAlreadyRegistred) {
            logger.error("User already registred at the event");
            throw new UserAlreadyRegistred();
        }

        var eventUser = new EventUser(event, user);

        repository.save(eventUser);

        return EventMapper.entityToDTO(event);
    }

    public void cancelEventSubscription(Long userId, Long eventId) {
        logger.info("Canceling subscription at the event id {}", eventId);

        var user = serviceUtils.getEntityOrThrow(userRepository, userId, new UserNotFound());
        var event = serviceUtils.getEntityOrThrow(eventRepository, eventId, new EventNotFound());

        var subscription = repository.findByEventIdAndUserId(event.getId(), user.getId());

        if (subscription.isEmpty()) {
            logger.info("Subscription not found at the event id {} and userId", eventId, userId);
            throw new SubscriptionNotFound();
        }

        repository.delete(subscription.get());
    }
}
