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

import com.jyotirmay.useridentity.dto.UpdateCredentialRequestTO;
import com.jyotirmay.useridentity.dto.UpdateCredentialResponseTO;
import com.jyotirmay.useridentity.entity.UserEntity;
import com.jyotirmay.useridentity.exception.UserNotFoundException;
import com.jyotirmay.useridentity.repository.UserIdentityRepository;
import com.jyotirmay.useridentity.service.UpdateUserCredentialService;
import com.jyotirmay.useridentity.util.IdentityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateUserCredentialServiceImpl implements UpdateUserCredentialService {

    private static final Logger LOGGER = LogManager.getLogger(UpdateUserCredentialServiceImpl.class);

    private final UserIdentityRepository userIdentityRepository;

    private final IdentityUtils identityUtils;

    public UpdateUserCredentialServiceImpl(UserIdentityRepository userIdentityRepository, IdentityUtils identityUtils) {
        this.userIdentityRepository = userIdentityRepository;
        this.identityUtils = identityUtils;
    }

    @Override
    public UpdateCredentialResponseTO updateUserCredential(UpdateCredentialRequestTO updateCredentialRequestTO) {
        UserEntity userEntity = userIdentityRepository.findByUsernameWithAllRelations(updateCredentialRequestTO.username(), true)
                .orElseThrow(() -> new UserNotFoundException("ERR404", "User with username %s does not exist.", updateCredentialRequestTO.username()));

        String encryptedPassword = identityUtils.encryptPassword(updateCredentialRequestTO.password());
        userEntity.getCredential().setPassword(encryptedPassword);
        userIdentityRepository.save(userEntity);

        LOGGER.info("Credential for user {} {} {} updated successfully for id: {} and username: {}", userEntity.getFirstName(),
                userEntity.getMiddleName(), userEntity.getLastName(), userEntity.getUserId(), updateCredentialRequestTO.username());

        return new UpdateCredentialResponseTO(String.format("Credential for user with username %s updated successfully",
                updateCredentialRequestTO.username()));
    }
}
