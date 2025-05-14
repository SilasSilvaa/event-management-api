package com.ssilvadev.event.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssilvadev.event.api.dto.user.request.RequestUserDTO;
import com.ssilvadev.event.api.dto.user.response.ResponseUserDTO;
import com.ssilvadev.event.api.exception.EmailAlreadyExists;
import com.ssilvadev.event.api.exception.RequiredNonNullObject;
import com.ssilvadev.event.api.exception.UserNotFound;
import com.ssilvadev.event.api.mapper.DTOConverter;
import com.ssilvadev.event.api.model.user.Email;
import com.ssilvadev.event.api.model.user.Gender;
import com.ssilvadev.event.api.model.user.LastName;
import com.ssilvadev.event.api.model.user.Name;
import com.ssilvadev.event.api.model.user.User;
import com.ssilvadev.event.api.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Page<ResponseUserDTO> getAll(Pageable pageable) {
        logger.info("Get all users");

        Page<User> entities = repository.findAll(pageable);

        var result = entities.stream()
                .map(entity -> DTOConverter.userToResponseDTO(entity))
                .toList();

        return new PageImpl<>(result, pageable, entities.getTotalElements());
    }

    public ResponseUserDTO create(RequestUserDTO userDTO) {
        logger.info("Creating an user");

        if (userDTO == null) {
            throw new RequiredNonNullObject();
        }

        validateEmailIsUnique(userDTO.email());

        var name = new Name(userDTO.name());
        var lastName = new LastName(userDTO.lastName());
        var email = new Email(userDTO.email());
        var gender = Gender.valueOf(userDTO.gender().name());

        var entity = new User(name, lastName, email, gender);

        return DTOConverter.userToResponseDTO(repository.save(entity));
    }

    public ResponseUserDTO findById(Long id) {
        logger.info("Finding user by id {}", id);

        var user = getUserOrThrow(id);

        ResponseUserDTO response = DTOConverter.userToResponseDTO(user);

        return response;
    }

    public ResponseUserDTO update(Long id, RequestUserDTO userDTO) {
        logger.info("Updating user with ID {}", id);

        var entity = getUserOrThrow(id);

        entity.updateProperties(userDTO);
        repository.save(entity);

        return DTOConverter.userToResponseDTO(entity);
    }

    public void delete(Long id) {
        logger.info("deleting user by id");

        ResponseUserDTO response = findById(id);
        var entity = DTOConverter.responseDTOToUser(response);
        repository.delete(entity);
    }

    private User getUserOrThrow(Long id) {
        var entity = repository.findById(id);

        if (entity.isEmpty()) {
            logger.error("User not found by id {}", id);
            throw new UserNotFound();
        }

        return entity.get();
    }

    private void validateEmailIsUnique(String email) {
        logger.info("Checking if a user already exists by email {}", email);

        if (repository.existsByEmail(new Email(email))) {
            logger.error("The user already exists by e-mail {}", email);
            throw new EmailAlreadyExists();
        }
    }
}
