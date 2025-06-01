package com.ssilvadev.event.api.controller.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ssilvadev.event.api.dto.event.request.RequestInPersonEventDTO;
import com.ssilvadev.event.api.dto.event.request.RequestRemoteEventDTO;
import com.ssilvadev.event.api.dto.event.response.ResponseEventDTO;
import com.ssilvadev.event.api.service.event.EventService;

@RestController
@RequestMapping("/api/event/v1")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<Page<ResponseEventDTO>> getAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var sortDirection = direction.equals("desc") ? Direction.DESC : Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));

        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping(path = { "/{id}", "/remote/{id}" })
    public ResponseEntity<ResponseEventDTO> getById(@PathVariable(name = "id", required = true) Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<ResponseEventDTO> createInPersonEvent(
            @RequestBody(required = true) RequestInPersonEventDTO dto) {

        ResponseEventDTO event = service.createInPersonEvent(dto);

        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(event.id()).toUri();

        return ResponseEntity.created(uri).body(event);
    }

    @PostMapping("/remote")
    public ResponseEntity<ResponseEventDTO> createRemoteEvent(
            @RequestBody(required = true) RequestRemoteEventDTO dto) {

        ResponseEventDTO remoteEvent = service.createRemoteEvent(dto);

        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(remoteEvent.id()).toUri();

        return ResponseEntity.created(uri).body(remoteEvent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable(name = "id") Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
