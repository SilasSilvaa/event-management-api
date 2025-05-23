package com.ssilvadev.event.api.dto.event.response;

import java.util.Date;

import com.ssilvadev.event.api.dto.event.AddressDTO;

public record ResponseEventDTO(
		Long id,
		String title,
		String description,
		Date eventDate,
		AddressDTO address,
		String eventUrl,
		String eventImageUrl,
		Integer capacity,
		boolean remote) {

}
