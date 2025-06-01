package com.ssilvadev.event.api.service.utils;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

public class ServiceUtils {
    private final Logger logger;

    public ServiceUtils(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public <T, ID> T getEntityOrThrow(JpaRepository<T, ID> repository, ID id, RuntimeException exception) {
        logger.info("Finding entity by id {}", id);
        Optional<T> entity = repository.findById(id);

        if (entity.isEmpty()) {
            logger.error("Entity not found by id {}", id);
            throw exception;
        }

        return entity.get();
    }
}
