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
import com.jyotirmay.useridentity.dto.GetUserResponseTO;
import com.jyotirmay.useridentity.dto.UserInformationTO;
import com.jyotirmay.useridentity.service.GetUserService;
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
public class UserIdentityControllerGetUserTest {

    @InjectMocks
    private UserQueryController userQueryController;

    @Mock
    private GetUserService getUserService;

    @Test
    void givenValidEmail_whenGetUserByEmailFromParamCalled_thenReturnsUserResponse() {

        UserInformationTO userInformationTO = TestDataGenerator.buildUserInformationTO();
        GetUserResponseTO getUserResponseTO = new GetUserResponseTO(userInformationTO);

        Mockito.when(getUserService.getUserByEmailId("john.doe@example.com")).thenReturn(getUserResponseTO);

        ResponseEntity<GetUserResponseTO> actualResponseEntity = userQueryController.getUserByEmailFromParam("john.doe@example.com");

        Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        Assertions.assertNotNull(actualResponseEntity.getBody());
        Assertions.assertEquals("john.doe@example.com", actualResponseEntity.getBody().userInformation().contact().email());

        Mockito.verify(getUserService, Mockito.times(1)).getUserByEmailId("john.doe@example.com");
    }

    @Test
    void givenValidEmail_whenGetUserByUsernameFromParamCalled_thenReturnsUserResponse() {

        UserInformationTO userInformationTO = TestDataGenerator.buildUserInformationTO();
        GetUserResponseTO getUserResponseTO = new GetUserResponseTO(userInformationTO);

        Mockito.when(getUserService.getUserByUsername("johndoe123")).thenReturn(getUserResponseTO);

        ResponseEntity<GetUserResponseTO> actualResponseEntity = userQueryController.getUserByUsernameFromParam("johndoe123");

        Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        Assertions.assertNotNull(actualResponseEntity.getBody());
        Assertions.assertEquals("john.doe@example.com", actualResponseEntity.getBody().userInformation().contact().email());

        Mockito.verify(getUserService, Mockito.times(1)).getUserByUsername("johndoe123");
    }

}
