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

import com.jyotirmay.useridentity.dto.GetUserResponseTO;
import com.jyotirmay.useridentity.dto.UserInformationTO;
import com.jyotirmay.useridentity.entity.UserEntity;
import com.jyotirmay.useridentity.exception.UserNotFoundException;
import com.jyotirmay.useridentity.mapper.UserIdentityMapper;
import com.jyotirmay.useridentity.repository.UserIdentityRepository;
import com.jyotirmay.useridentity.service.GetUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetUserServiceImpl implements GetUserService {

    private static final Logger LOGGER = LogManager.getLogger(GetUserServiceImpl.class);

    private final UserIdentityRepository userIdentityRepository;

    private final UserIdentityMapper userIdentityMapper;

    public GetUserServiceImpl(UserIdentityRepository userIdentityRepository, UserIdentityMapper userIdentityMapper) {
        this.userIdentityRepository = userIdentityRepository;
        this.userIdentityMapper = userIdentityMapper;
    }

    @Override
    public GetUserResponseTO getUserByEmail(String emailId) {
        UserEntity userEntity = userIdentityRepository.findByEmailWithAllRelations(emailId, true)
                .orElseThrow(() -> new UserNotFoundException("ERR404", "User with emailId %s does not exist.", emailId));

        LOGGER.info("User {} {} {} fetched successfully for id: {} and email: {}", userEntity.getFirstName(),
                userEntity.getMiddleName(), userEntity.getLastName(), userEntity.getUserId(), emailId);

        UserInformationTO userInformationTO = userIdentityMapper.buildUserInformationTOFromUserEntity(userEntity);

        return new GetUserResponseTO(userInformationTO);
    }

    @Override
    public GetUserResponseTO getUserByUsername(String username) {
        UserEntity userEntity = userIdentityRepository.findByUsernameWithAllRelations(username, true)
                .orElseThrow(() -> new UserNotFoundException("ERR404", "User with username %s does not exist.", username));

        LOGGER.info("User {} {} {} fetched successfully for id: {} and username: {}", userEntity.getFirstName(),
                userEntity.getMiddleName(), userEntity.getLastName(), userEntity.getUserId(), username);

        UserInformationTO userInformationTO = userIdentityMapper.buildUserInformationTOFromUserEntity(userEntity);

        return new GetUserResponseTO(userInformationTO);
    }
}
