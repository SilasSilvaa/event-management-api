package com.ssilvadev.event.api.integrationtests.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ssilvadev.event.api.integrationtests.testcontainers.AbstractIntegrationTest;
import com.ssilvadev.event.api.model.user.Email;
import com.ssilvadev.event.api.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private UserRepository repository;

    @Test
    @Order(1)
    void shouldReturnTrueWhenEmailAlreadyExists() {
        var email = new Email("joao.silva@example.com");
        assertTrue(repository.existsByEmail(email));
    }

    @Test
    @Order(2)
    void shouldReturnFalseWhenEmailNotExists() {
        var email = new Email("joao1.silva@example.com");
        assertFalse(repository.existsByEmail(email));
    }
}
