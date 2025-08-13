/*
 * Copyright (c) 2025 Jyotirmay Gupta
 *
 * Project: User Identity Service
 * Description: This is a personal project by Jyotirmay Gupta that implements a
 * user identity management service using Spring Boot. It provides functionality to
 * register and maintain user identities within a system.
 *
 * This code is intended for educational and personal use, demonstrating core backend
 * concepts such as RESTful API design, user registration, integration
 * with persistent storage using Spring Boot.
 *
 * Licensed under the Apache License Version 2.0. See LICENSE file for more details.
 */

package com.jyotirmay.useridentity.repository;

import com.jyotirmay.useridentity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserIdentityRepository extends JpaRepository<UserEntity, UUID> {

    @Query("SELECT u FROM UserEntity u " +
            "JOIN FETCH u.contact c " +
            "LEFT JOIN FETCH u.address a " +
            "LEFT JOIN FETCH u.credential cr " +
            "WHERE c.email = :email and u.active = :active")
    Optional<UserEntity> findByEmailWithAllRelations(@Param("email") String email, @Param("active") Boolean active);

    @Query("SELECT u FROM UserEntity u " +
            "JOIN FETCH u.credential cr " +
            "LEFT JOIN FETCH u.contact c " +
            "LEFT JOIN FETCH u.address a " +
            "WHERE cr.username = :username and u.active = :active")
    Optional<UserEntity> findByUsernameWithAllRelations(@Param("username") String username, @Param("active") Boolean active);
}
