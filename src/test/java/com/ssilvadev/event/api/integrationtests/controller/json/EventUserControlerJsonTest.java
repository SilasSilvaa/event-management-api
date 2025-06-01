package com.ssilvadev.event.api.integrationtests.controller.json;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import com.ssilvadev.event.api.configs.TestConfigs;
import com.ssilvadev.event.api.dto.eventtest.response.ResponseEventDTO;
import com.ssilvadev.event.api.dto.usertest.response.WrapperResponseDTO;
import com.ssilvadev.event.api.integrationtests.testcontainers.AbstractIntegrationTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventUserControlerJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;

    @BeforeAll
    static void setUp() {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, TestConfigs.HEADER_PARAM_ORIGIN)
                .setPort(TestConfigs.SERVER_PORT)
                .setBasePath("/api/management/v1")
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    void shouldGetEventSubscribers() {
        var result = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("page", 0,
                        "size", 10,
                        "direction", "asc")
                .when()
                .pathParam("eventId", 1L)
                .get("/subscriptions/{eventId}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .as(new TypeRef<WrapperResponseDTO>() {
                });

        assertNotNull(result);
        assertNotNull(result.content());
        assertTrue(result.content().size() == 2);
    }

    @Test
    @Order(2)
    void shouldThrowExceptionWhenGetEventsRegistredWithInvlidEventId() {
        given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("page", 0,
                        "size", 10,
                        "direction", "asc")
                .pathParam("eventId", "InvalidId")
                .when()
                .get("/subscriptions/{eventId}")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(3)
    void shouldThrowExecptionWhenGetEventsRegistredWithNonExistsEventId() {
        given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("page", 0,
                        "size", 10,
                        "direction", "asc")
                .pathParam("eventId", 4L)
                .when()
                .get("/subscriptions/{eventId}")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(4)
    void shouldGetEventsRegistred() {
        var result = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("page", 0,
                        "size", 10,
                        "direction", "asc")
                .pathParam("userId", 1L)
                .when()
                .get("/registrations/{userId}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .as(new TypeRef<com.ssilvadev.event.api.dto.eventtest.response.WrapperResponseDTO>() {
                });

        assertNotNull(result);
        assertNotNull(result.content());
        assertTrue(result.content().size() == 1);
    }

    @Test
    @Order(5)
    void shouldThrowExceptionWhenGetEventsRegistredWithInvlidUserId() {
        given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("page", 0,
                        "size", 10,
                        "direction", "asc")
                .pathParam("userId", "InvalidId")
                .when()
                .get("/registrations/{userId}")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(6)
    void shouldThrowExecptionWhenGetEventsRegistredWithNonExistsUserId() {
        given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("page", 0,
                        "size", 10,
                        "direction", "asc")
                .pathParam("userId", 11L)
                .when()
                .get("/registrations/{userId}")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(7)
    void shouldRegisterOnEvent() {
        var result = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("userId", 1L)
                .pathParam("eventId", 1L)
                .when()
                .post("/register/{userId}/{eventId}")
                .then()
                .statusCode(201)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .as(new TypeRef<ResponseEventDTO>() {
                });

        assertNotNull(result);

        assertNotNull(result.id());
        assertTrue(result.id() > 0);

        assertEquals("Tech Conference 2025", result.title());
        assertEquals(
                "A major conference focused on the latest trends in software development, AI, and cloud computing.",
                result.description());
        assertNotNull(result.eventDate());
        assertFalse(result.remote());

        assertEquals("https://example.com/tech-conference-2025", result.eventUrl());
        assertEquals("https://example.com/images/tech-conference.jpg", result.eventImageUrl());

        assertEquals(Integer.valueOf(500), result.capacity());

        assertEquals("123 Innovation Ave", result.address().street());
        assertEquals("Downtown", result.address().neighborhood());
        assertEquals("São Paulo", result.address().city());
        assertEquals("SP", result.address().state());
        assertEquals("01001000", result.address().cep());
    }

    @Test
    @Order(8)
    void shoulThrowExceptionWhendRegisterOnEventWithInvalidEventId() {
        given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("userId", "invalidId")
                .pathParam("eventId", 1L)
                .when()
                .post("/register/{userId}/{eventId}")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(9)
    void shoulThrowExceptionWhendRegisterOnEventWithInvalidUserId() {
        given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("userId", 1L)
                .pathParam("eventId", "invalidId")
                .when()
                .post("/register/{userId}/{eventId}")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(10)
    void shoulThrowExceptionWhendRegisterOnEventWithNonExistEventId() {
        given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("userId", 1L)
                .pathParam("eventId", 10L)
                .when()
                .post("/register/{userId}/{eventId}")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(11)
    void shoulThrowExceptionWhendRegisterOnEventWithNonExistUserId() {
        given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("userId", 11L)
                .pathParam("eventId", 1L)
                .when()
                .post("/register/{userId}/{eventId}")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(12)
    void shouldCancelSubscription() {
        given(specification)
                .pathParam("eventId", 1L)
                .pathParam("userId", 1L)
                .when()
                .delete("/cancelSubscription/{eventId}/{userId}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(13)
    void shouldTheowExceptionWhenCancelSubscriptionWithInvalidId() {
        given(specification)
                .pathParam("eventId", "InvalidId")
                .pathParam("userId", 1L)
                .when()
                .delete("/cancelSubscription/{eventId}/{userId}")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(14)
    void shouldTheowExceptionWhenCancelSubscriptionWithNonExistEventId() {
        given(specification)
                .pathParam("eventId", 10L)
                .pathParam("userId", 1L)
                .when()
                .delete("/cancelSubscription/{eventId}/{userId}")
                .then()
                .statusCode(404);
    }
}
