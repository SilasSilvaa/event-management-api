package com.ssilvadev.event.api.controller.eventUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ssilvadev.event.api.dto.event.response.ResponseEventDTO;
import com.ssilvadev.event.api.dto.user.response.ResponseUserDTO;
import com.ssilvadev.event.api.service.eventUser.EventUserService;

@RestController
@RequestMapping("/api/management/v1")
public class EventUserController {

    private final EventUserService service;

    public EventUserController(EventUserService service) {
        this.service = service;
    }

    @GetMapping("/subscriptions/{eventId}")
    public ResponseEntity<Page<ResponseUserDTO>> getEventSubscribers(
            @PathVariable(name = "eventId") Long eventId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var sortDirection = direction.equals("desc") ? Direction.DESC : Direction.ASC;
        var pageable = PageRequest.of(page, size, Sort.by(sortDirection, "user"));

        var eventSubscribers = service.getEventSubscribers(eventId, pageable);

        return ResponseEntity.ok(eventSubscribers);
    }

    @GetMapping("/registrations/{userId}")
    public ResponseEntity<Page<ResponseEventDTO>> getEventsRegistred(
            @PathVariable(name = "userId") Long userId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var sortDirection = direction.equals("desc") ? Direction.DESC : Direction.ASC;
        var pageable = PageRequest.of(page, size, Sort.by(sortDirection, "user"));

        var eventSubscribers = service.getEventsRegistred(userId, pageable);

        return ResponseEntity.ok(eventSubscribers);
    }

    @PostMapping("/register/{userId}/{eventId}")
    public ResponseEntity<ResponseEventDTO> registerOnEvent(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name = "eventId") Long eventId) {

        var response = service.registerOnEvent(userId, eventId);

        var uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/management/v1/registrations/{userId}")
                .buildAndExpand(userId)
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("/cancelSubscription/{eventId}/{userId}")
    public ResponseEntity<?> cancelEventSubscription(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name = "eventId") Long eventId) {

        service.cancelEventSubscription(userId, eventId);

        return ResponseEntity.noContent().build();
    }
}
