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
package com.jyotirmay.useridentity.controller;

import com.jyotirmay.useridentity.data.TestDataGenerator;
import com.jyotirmay.useridentity.dto.UpdateUserRequestTO;
import com.jyotirmay.useridentity.dto.UpdateUserResponseTO;
import com.jyotirmay.useridentity.dto.UserInformationTO;
import com.jyotirmay.useridentity.service.UpdateUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UserIdentityControllerUpdateUserTest {

    @InjectMocks
    private UserUpdateController userUpdateController;

    @Mock
    private UpdateUserService updateUserService;

    @Test
    void givenValidUpdateUserRequest_whenUpdateUserByEmailFromParamCalled_thenReturnsUpdatedUserResponse() {

        UpdateUserRequestTO updateUserRequestTO = TestDataGenerator.buildUpdateUserRequestTO();
        UserInformationTO userInformationTO = TestDataGenerator.buildUserInformationTO();
        UpdateUserResponseTO updateUserResponseTO = new UpdateUserResponseTO(userInformationTO);

        Mockito.when(updateUserService.updateUserByEmailId(updateUserRequestTO, "john.doe@example.com")).thenReturn(updateUserResponseTO);

        ResponseEntity<UpdateUserResponseTO> actualResponseEntity = userUpdateController.updateUserByEmailFromParam(updateUserRequestTO, "john.doe@example.com");

        Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        Assertions.assertNotNull(actualResponseEntity.getBody());
        Assertions.assertEquals("john.doe@example.com", actualResponseEntity.getBody().userInformation().contact().email());

        Mockito.verify(updateUserService, Mockito.times(1)).updateUserByEmailId(updateUserRequestTO, "john.doe@example.com");
    }

    @Test
    void givenValidUpdateUserRequest_whenUpdateUserByUsernameFromParamCalled_thenReturnsUpdatedUserResponse() {

        UpdateUserRequestTO updateUserRequestTO = TestDataGenerator.buildUpdateUserRequestTO();
        UserInformationTO userInformationTO = TestDataGenerator.buildUserInformationTO();
        UpdateUserResponseTO updateUserResponseTO = new UpdateUserResponseTO(userInformationTO);

        Mockito.when(updateUserService.updateUserByUsername(updateUserRequestTO, "johndoe123")).thenReturn(updateUserResponseTO);

        ResponseEntity<UpdateUserResponseTO> actualResponseEntity = userUpdateController.updateUserByUsernameFromParam(updateUserRequestTO, "johndoe123");

        Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        Assertions.assertNotNull(actualResponseEntity.getBody());
        Assertions.assertEquals("john.doe@example.com", actualResponseEntity.getBody().userInformation().contact().email());

        Mockito.verify(updateUserService, Mockito.times(1)).updateUserByUsername(updateUserRequestTO, "johndoe123");
    }
}
