package com.ssilvadev.event.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssilvadev.event.api.dto.user.request.RequestUserDTO;
import com.ssilvadev.event.api.dto.user.response.ResponseUserDTO;
import com.ssilvadev.event.api.service.UserService;

@RestController
@RequestMapping("/api/user/v1")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ResponseUserDTO>> getAllUsers(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var sortDirection = direction.equals("desc") ? Direction.DESC : Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));

        var allUsers = service.getAllUsers(pageable);

        return ResponseEntity.ok(allUsers);
    }

    @PostMapping
    public ResponseEntity<ResponseUserDTO> createUser(@RequestBody(required = true) RequestUserDTO userDTO) {
        return ResponseEntity.ok(service.createUser(userDTO));
    }
}
