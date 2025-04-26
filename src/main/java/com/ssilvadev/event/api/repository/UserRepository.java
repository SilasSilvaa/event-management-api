package com.ssilvadev.event.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssilvadev.event.api.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
