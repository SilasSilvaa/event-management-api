package com.ssilvadev.event.api.integrationtests.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ssilvadev.event.api.integrationtests.testcontainers.AbstractIntegrationTest;
import com.ssilvadev.event.api.repository.userEvent.EventUserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventUserRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private EventUserRepository repository;

    @Test
    @Order(1)
    void shouldFindEventsById() {
        var pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "user"));

        var result = repository.findByEventId(1L, pageable);

        assertNotNull(result);
        assertTrue(result.getContent().size() == 2);

        assertTrue(result.getContent().get(0).getId() == 2);
        assertTrue(result.getContent().get(1).getId() == 10);
    }

    @Test
    @Order(2)
    void shouldFindUsersById() {
        var pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "user"));

        var result = repository.findByUserId(1L, pageable);

        assertNotNull(result);
        assertTrue(result.getContent().size() == 1);

        assertTrue(result.getContent().get(0).getId() == 2);
    }

    @Test
    @Order(3)
    void shouldExistsEventAndUserById() {
        assertTrue(repository.existsByEventIdAndUserId(1L, 2L));
    }

    @Test
    @Order(4)
    void shouldFindEventUserByUserAndEventId() {
        var result = repository.findByEventIdAndUserId(1L, 2L);

        assertNotNull(result);
        assertNotNull(result.get().getId());
        assertNotNull(result.get().getEvent());
        assertNotNull(result.get().getUser());
    }

}
