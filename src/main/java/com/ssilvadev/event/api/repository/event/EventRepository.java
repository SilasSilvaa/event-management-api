package com.ssilvadev.event.api.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssilvadev.event.api.model.event.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
