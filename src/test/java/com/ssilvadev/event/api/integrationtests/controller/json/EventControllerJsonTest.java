package com.ssilvadev.event.api.integrationtests.controller.json;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import com.ssilvadev.event.api.configs.TestConfigs;
import com.ssilvadev.event.api.dto.event.request.RequestInPersonEventDTO;
import com.ssilvadev.event.api.dto.event.request.RequestRemoteEventDTO;
import com.ssilvadev.event.api.dto.eventtest.response.ResponseEventDTO;
import com.ssilvadev.event.api.dto.eventtest.response.WrapperResponseDTO;
import com.ssilvadev.event.api.integrationtests.testcontainers.AbstractIntegrationTest;
import com.ssilvadev.event.api.mocks.MockEvent;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventControllerJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static MockEvent mock;

	private static RequestRemoteEventDTO remoteEventDTO;
	private static RequestInPersonEventDTO inPersonEventDTO;

	@BeforeAll
	static void setUp() {
		mock = new MockEvent();
		remoteEventDTO = mock.mockRemoteEventDTO();
		inPersonEventDTO = mock.mockInPersonEventDTO();

		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, TestConfigs.HEADER_PARAM_ORIGIN)
				.setPort(TestConfigs.SERVER_PORT)
				.setBasePath("/api/event/v1")
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}

	@Test
	@Order(1)
	void shouldGetAllEvents() {

		var result = given(specification)
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

		assertNotNull(result);
		assertTrue(result.content().size() == 3);

		var eventOne = result.content().get(0);

		assertNotNull(eventOne);
		assertNotNull(eventOne.id());

		assertEquals(Long.valueOf(3), eventOne.id());
		assertEquals("AI & Machine Learning Bootcamp", eventOne.title());
		assertEquals("An intensive 3-day bootcamp on applied machine learning and AI models.",
				eventOne.description());

		assertNotNull(eventOne.eventDate());

		assertEquals("https://example.com/ai-bootcamp", eventOne.eventUrl());
		assertEquals("https://example.com/images/ai-bootcamp.jpg", eventOne.eventImageUrl());
		assertEquals(Integer.valueOf(100), eventOne.capacity());

		assertNotNull(eventOne.address());

		assertEquals("456 Knowledge St", eventOne.address().street());
		assertEquals("Tech Park", eventOne.address().neighborhood());
		assertEquals("Campinas", eventOne.address().city());
		assertEquals("SP", eventOne.address().state());
		assertEquals("13010000", eventOne.address().cep());

		assertFalse(eventOne.remote());

		var eventTwo = result.content().get(1);

		assertNotNull(eventTwo);
		assertNotNull(eventTwo.id());

		assertEquals(Long.valueOf(2), eventTwo.id());
		assertEquals("Remote Dev Meetup", eventTwo.title());
		assertEquals("An online meetup for remote developers around the world to connect and share ideas.",
				eventTwo.description());

		assertNotNull(eventTwo.eventDate());

		assertEquals("https://example.com/remote-dev-meetup", eventTwo.eventUrl());
		assertEquals("https://example.com/images/remote-meetup.jpg", eventTwo.eventImageUrl());
		assertEquals(Integer.valueOf(300), eventTwo.capacity());

		assertNull(eventTwo.address());

		assertTrue(eventTwo.remote());
	}

	@Test
	@Order(2)
	void shouldCreateInPersonEvent() {

		var result = given(specification)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(inPersonEventDTO)
				.when()
				.post("/")
				.then()
				.statusCode(201)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.extract()
				.body()
				.as(new TypeRef<ResponseEventDTO>() {
				});

		assertNotNull(result);

		assertNotNull(result.id());
		assertEquals(Long.valueOf(4), result.id());

		assertEquals("Tech Event", result.title());
		assertEquals("https://www.techevent.com/event/details", result.eventUrl());
		assertEquals("https://www.techevent.com/eventbanner.png", result.eventImageUrl());
		assertEquals("Take part in the biggest technology event of the year! There will be three days of talks.",
				result.description());

		assertEquals(Integer.valueOf(150), result.capacity());

		assertNotNull(result.eventDate());

		assertNotNull(result.address());

		assertEquals("Av. Main 123", result.address().street());
		assertEquals("Neighborhood SP", result.address().neighborhood());
		assertEquals("São Paulo", result.address().city());
		assertEquals("SP", result.address().state());
		assertEquals("01223098", result.address().cep());
	}

	@Test
	@Order(3)
	void shouldCreateRemoteEvent() {

		var result = given(specification)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(remoteEventDTO)
				.when()
				.post("/remote")
				.then()
				.statusCode(201)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.extract()
				.body()
				.as(new TypeRef<ResponseEventDTO>() {
				});

		assertNotNull(result);

		assertNotNull(result.id());
		assertEquals(Long.valueOf(5), result.id());

		assertEquals("Tech Event 2", result.title());
		assertEquals("https://www.techevent.com/event/2/details", result.eventUrl());
		assertEquals("https://www.techevent.com/2/eventbanner.png", result.eventImageUrl());
		assertEquals("Take part in the biggest technology 2º event of the year! There will be three days of talks.",
				result.description());

		assertEquals(Integer.valueOf(100), result.capacity());

		assertNotNull(result.eventDate());

		assertNull(result.address());
	}

	@Test
	@Order(4)
	void shouldThowExceptionWhenCreateAnyEventWithNullBody() {
		given(specification)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.when()
				.post("/")
				.then()
				.statusCode(400);
	}

	@Test
	@Order(5)
	void shouldGetEventById() {
		var result = given(specification)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.when()
				.pathParam("id", 5L)
				.get("/{id}")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.as(new TypeRef<ResponseEventDTO>() {
				});

		assertNotNull(result);

		assertNotNull(result.id());

		assertEquals(Long.valueOf(5), result.id());

		assertEquals("Tech Event 2", result.title());
		assertEquals("https://www.techevent.com/event/2/details", result.eventUrl());
		assertEquals("https://www.techevent.com/2/eventbanner.png", result.eventImageUrl());
		assertEquals("Take part in the biggest technology 2º event of the year! There will be three days of talks.",
				result.description());

		assertEquals(Integer.valueOf(100), result.capacity());

		assertNotNull(result.eventDate());

		assertNull(result.address());
	}

	@Test
	@Order(6)
	void shouldThowExceptionWhenGetEventWithInvalidId() {
		given(specification)
				.pathParam("id", "invalidId")
				.when()
				.delete("{id}")
				.then()
				.statusCode(400);
	}

	@Test
	@Order(7)
	void shouldDeleteEventByID() {
		given(specification)
				.pathParam("id", 4L)
				.when()
				.delete("/{id}")
				.then()
				.statusCode(204);
	}

	@Test
	@Order(8)
	void shouldThowExceptionWhenDeleteEventWithInvalidId() {
		given(specification)
				.pathParam("id", "invalidId")
				.when()
				.delete("{id}")
				.then()
				.statusCode(400);
	}
}
