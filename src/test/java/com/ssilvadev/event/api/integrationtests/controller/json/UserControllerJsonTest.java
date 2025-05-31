package com.ssilvadev.event.api.integrationtests.controller.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import com.ssilvadev.event.api.configs.TestConfigs;
import com.ssilvadev.event.api.dto.user.request.RequestUserDTO;
import com.ssilvadev.event.api.dto.user.response.Gender;
import com.ssilvadev.event.api.dto.usertest.response.ResponseUserDTO;
import com.ssilvadev.event.api.dto.usertest.response.WrapperResponseDTO;
import com.ssilvadev.event.api.integrationtests.testcontainers.AbstractIntegrationTest;
import com.ssilvadev.event.api.mocks.MockUser;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static MockUser mockUser;
    private static RequestUserDTO requestUser;

    @BeforeAll
    static void setup() {
        mockUser = new MockUser();
        requestUser = mockUser.mockUserDto();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, TestConfigs.HEADER_PARAM_ORIGIN)
                .setPort(TestConfigs.SERVER_PORT)
                .setBasePath("/api/user/v1")
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    void shouldCreateUser() {
        var response = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestUser)
                .when()
                .post()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .as(new TypeRef<ResponseUserDTO>() {
                });

        assertNotNull(response);
        assertNotNull(response.id());

        assertTrue(response.id() > 0);

        assertEquals("Wood", requestUser.name());
        assertEquals("Phethean", requestUser.lastName());
        assertEquals("wphethean0@ebay.com", requestUser.email());
        assertEquals("MALE", requestUser.gender().name());
    }

    @Test
    @Order(2)
    void shouldThowExceptionWhenCreateUserWithNullBody() {
        given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post()
                .then()
                .statusCode(400);
    }

    @Test
    @Order(3)
    void shouldGetAllUser() {
        var response = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("page", 0,
                        "size", 10,
                        "direction", "asc")
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(new TypeRef<WrapperResponseDTO>() {
                });

        assertNotNull(response);
        assertEquals(10, response.content().size());
        var userOne = response.content().get(0);

        assertNotNull(userOne.id());
        assertTrue(userOne.id() > 0);

        assertEquals("Ana", userOne.name());
        assertEquals("Costa", userOne.lastName());
        assertEquals("ana.costa@example.com", userOne.email());
        assertEquals("FEMALE", userOne.gender().name());

        var userEight = response.content().get(7);

        assertNotNull(userEight.id());
        assertTrue(userEight.id() > 0);

        assertEquals("Maria", userEight.name());
        assertEquals("Souza", userEight.lastName());
        assertEquals("maria.souza@example.com", userEight.email());
        assertEquals("FEMALE", userEight.gender().name());
    }

    @Test
    @Order(4)
    void shouldGetAnUser() {
        var response = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", 1L)
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(new TypeRef<ResponseUserDTO>() {
                });

        assertNotNull(response);
        assertNotNull(response.id());

        assertEquals("João", response.name());
        assertEquals("Silva", response.lastName());
        assertEquals("joao.silva@example.com", response.email());
        assertEquals("MALE", response.gender().name());
    }

    @Test
    @Order(5)
    void shouldThowExceptionWhenGetUserWithInvalidId() {
        given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", "invalidId")
                .when()
                .get("{id}")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(6)
    void shouldUpdateUser() {
        var request = new RequestUserDTO(
                "Wood",
                "Phethean John",
                "wphethean0@ebay.com",
                Gender.MALE);

        var response = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .pathParam("id", 11L)
                .when()
                .put("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .as(new TypeRef<ResponseUserDTO>() {
                });

        assertNotNull(response);
        assertNotNull(response.id());

        assertTrue(response.id() > 0);

        assertEquals("Wood", response.name());
        assertEquals("Phethean John", response.lastName());
        assertEquals("wphethean0@ebay.com", response.email());
        assertEquals("MALE", response.gender().name());
    }

    @Test
    @Order(7)
    void shouldThowExceptionWhenUpdateWithNullBody() {
        given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", 11L)
                .when()
                .put("{id}")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(8)
    void shouldThowExceptionWhenUpdateWithInvalidId() {
        var request = new RequestUserDTO(
                "Wood",
                "Phethean John",
                "wphethean0@ebay.com",
                Gender.MALE);

        given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .pathParam("id", "invalidId")
                .when()
                .put("{id}")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(9)
    void shoulDeletUser() {
        given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .pathParam("id", 11L)
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(10)
    void shouldThowExceptionWhenDeleteUserWithInvalidId() {
        given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", "invalidId")
                .when()
                .delete("{id}")
                .then()
                .statusCode(400);
    }
}
