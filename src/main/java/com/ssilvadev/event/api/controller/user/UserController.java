package com.ssilvadev.event.api.controller.user;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssilvadev.event.api.dto.user.request.RequestUserDTO;
import com.ssilvadev.event.api.dto.user.response.ResponseUserDTO;
import com.ssilvadev.event.api.service.user.UserService;

@RestController
@RequestMapping("/api/user/v1")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<Page<ResponseUserDTO>> getAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var sortDirection = direction.equals("desc") ? Direction.DESC : Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));

        var allUsers = service.getAll(pageable);

        return ResponseEntity.ok(allUsers);
    }

    @PostMapping
    public ResponseEntity<ResponseUserDTO> create(@RequestBody(required = true) RequestUserDTO userDTO) {
        return ResponseEntity.ok(service.create(userDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> findById(@PathVariable(name = "id", required = true) Long id) {

        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> update(
            @PathVariable(name = "id", required = true) Long id,
            @RequestBody(required = true) RequestUserDTO userDTO) {

        return ResponseEntity.ok(service.update(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id", required = true) Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
