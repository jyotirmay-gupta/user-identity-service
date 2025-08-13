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

import java.util.function.Function;

@ExtendWith(MockitoExtension.class)
public class UserQueryControllerTest {

    @InjectMocks
    private UserQueryController userQueryController;

    @Mock
    private GetUserService getUserService;

    @Test
    void givenValidEmail_whenGetUserByEmailFromParamCalled_thenReturnsUserResponse() {

        testGetUserByEmail(email -> userQueryController.getUserByEmailFromParam(email));
    }

    @Test
    void givenValidEmail_whenGetUserByEmailFromHeaderCalled_thenReturnsUserResponse() {

        testGetUserByEmail(email -> userQueryController.getUserByEmailIdFromHeader(email));
    }

    @Test
    void givenValidEmail_whenGetUserByEmailFromPathVariableCalled_thenReturnsUserResponse() {

        testGetUserByEmail(email -> userQueryController.getUserByEmailIdFromPathVariable(email));
    }

    void testGetUserByEmail(Function<String, ResponseEntity<GetUserResponseTO>> controllerMethod) {
        String email = "john.doe@example.com";

        UserInformationTO userInformationTO = TestDataGenerator.buildUserInformationTO();
        GetUserResponseTO getUserResponseTO = new GetUserResponseTO(userInformationTO);

        Mockito.when(getUserService.getUserByEmail(email)).thenReturn(getUserResponseTO);

        ResponseEntity<GetUserResponseTO> actualResponseEntity = controllerMethod.apply(email);

        Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        Assertions.assertNotNull(actualResponseEntity.getBody());
        Assertions.assertEquals(email, actualResponseEntity.getBody().userInformation().contact().email());

        Mockito.verify(getUserService, Mockito.times(1)).getUserByEmail(email);
    }

    @Test
    void givenValidEmail_whenGetUserByUsernameFromParamCalled_thenReturnsUserResponse() {

        testGetUserByUsername(username -> userQueryController.getUserByUsernameFromParam(username));
    }

    @Test
    void givenValidEmail_whenGetUserByUsernameFromHeaderCalled_thenReturnsUserResponse() {

        testGetUserByUsername(username -> userQueryController.getUserByUsernameFromHeader(username));
    }

    @Test
    void givenValidEmail_whenGetUserByUsernameFromPathVariableCalled_thenReturnsUserResponse() {

        testGetUserByUsername(username -> userQueryController.getUserByUsernameFromPathVariable(username));
    }

    void testGetUserByUsername(Function<String, ResponseEntity<GetUserResponseTO>> controllerMethod) {
        String username = "johndoe123";

        UserInformationTO userInformationTO = TestDataGenerator.buildUserInformationTO();
        GetUserResponseTO getUserResponseTO = new GetUserResponseTO(userInformationTO);

        Mockito.when(getUserService.getUserByUsername(username)).thenReturn(getUserResponseTO);

        ResponseEntity<GetUserResponseTO> actualResponseEntity = controllerMethod.apply(username);

        Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        Assertions.assertNotNull(actualResponseEntity.getBody());
        Assertions.assertEquals("john.doe@example.com", actualResponseEntity.getBody().userInformation().contact().email());

        Mockito.verify(getUserService, Mockito.times(1)).getUserByUsername(username);
    }

}
