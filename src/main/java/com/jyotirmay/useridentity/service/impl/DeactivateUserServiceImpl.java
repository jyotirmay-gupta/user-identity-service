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

package com.jyotirmay.useridentity.service.impl;

import com.jyotirmay.useridentity.dto.DeactivateUserResponseTO;
import com.jyotirmay.useridentity.entity.UserEntity;
import com.jyotirmay.useridentity.exception.UserNotFoundException;
import com.jyotirmay.useridentity.repository.UserIdentityRepository;
import com.jyotirmay.useridentity.service.DeactivateUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class DeactivateUserServiceImpl implements DeactivateUserService {

    private static final Logger LOGGER = LogManager.getLogger(DeactivateUserServiceImpl.class);

    private final UserIdentityRepository userIdentityRepository;

    public DeactivateUserServiceImpl(UserIdentityRepository userIdentityRepository) {
        this.userIdentityRepository = userIdentityRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, label = "delete_user_by_email_tx")
    public DeactivateUserResponseTO deactivateUserByEmailId(String emailId) {
        UserEntity userEntity = userIdentityRepository.findByEmailWithAllRelations(emailId, true)
                .orElseThrow(() -> new UserNotFoundException("ERR404", "User with emailId %s does not exist.", emailId));

        userEntity.setActive(false);
        userEntity.getAddress().setActive(false);
        userEntity.getContact().setActive(false);
        userEntity.getCredential().setActive(false);
        userIdentityRepository.save(userEntity);

        LOGGER.info("User {} {} {} deactivated successfully for id: {} and email: {}", userEntity.getFirstName(),
                userEntity.getMiddleName(), userEntity.getLastName(), userEntity.getUserId(), emailId);

        return new DeactivateUserResponseTO(String.format("User with emailId %s deleted successfully", emailId));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, label = "delete_user_by_username_tx")
    public DeactivateUserResponseTO deactivateUserByUsername(String username) {
        UserEntity userEntity = userIdentityRepository.findByUsernameWithAllRelations(username, true)
                .orElseThrow(() -> new UserNotFoundException("ERR404", "User with username %s does not exist.", username));

        userEntity.setActive(false);
        userEntity.getAddress().setActive(false);
        userEntity.getContact().setActive(false);
        userEntity.getCredential().setActive(false);
        userIdentityRepository.save(userEntity);

        LOGGER.info("User {} {} {} deactivated successfully for id: {} and username: {}", userEntity.getFirstName(),
                userEntity.getMiddleName(), userEntity.getLastName(), userEntity.getUserId(), username);

        return new DeactivateUserResponseTO(String.format("User with username %s deleted successfully", username));
    }
}
