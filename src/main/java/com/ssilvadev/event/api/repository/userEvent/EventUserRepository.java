package com.ssilvadev.event.api.repository.userEvent;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssilvadev.event.api.model.event.Event;
import com.ssilvadev.event.api.model.user.User;
import com.ssilvadev.event.api.model.userEvent.EventUser;
import com.ssilvadev.event.api.model.userEvent.EventUserId;

@Repository
public interface EventUserRepository extends JpaRepository<EventUser, EventUserId> {

    @Query("SELECT eu.user FROM EventUser eu WHERE eu.event.id = :eventId")
    Page<User> findByEventId(Long eventId, Pageable pageable);

    @Query("SELECT eu.event FROM EventUser eu WHERE eu.user.id = :userId")
    Page<Event> findByUserId(Long userId, Pageable pageable);

    boolean existsByEventIdAndUserId(Long eventId, Long userId);

    Optional<EventUser> findByEventIdAndUserId(Long eventId, Long userId);
}
