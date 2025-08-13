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
import com.jyotirmay.useridentity.dto.CredentialTO;
import com.jyotirmay.useridentity.dto.RegisterUserRequestTO;
import com.jyotirmay.useridentity.dto.RegisterUserResponseTO;
import com.jyotirmay.useridentity.dto.UserInformationTO;
import com.jyotirmay.useridentity.service.RegisterUserService;
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
class UserRegistrationControllerTest {

    @InjectMocks
    private UserRegistrationController userRegistrationController;

    @Mock
    private RegisterUserService registerUserService;

    @Test
    void givenValidRegisterUserRequest_whenRegisterUserCalled_thenReturnsCreatedUserResponse() throws Exception {

        RegisterUserRequestTO registerUserRequestTO = TestDataGenerator.buildRegisterUserRequestTO();
        UserInformationTO userInformationTO = TestDataGenerator.buildUserInformationTO();
        CredentialTO credentialTO = new CredentialTO("johndoe123", "password");
        RegisterUserResponseTO registerUserResponseTO = new RegisterUserResponseTO(userInformationTO, credentialTO);

        Mockito.when(registerUserService.registerUser(registerUserRequestTO)).thenReturn(registerUserResponseTO);

        ResponseEntity<RegisterUserResponseTO> actualResponseEntity = userRegistrationController.registerUser(registerUserRequestTO);

        Assertions.assertEquals(HttpStatus.CREATED, actualResponseEntity.getStatusCode());
        Assertions.assertNotNull(actualResponseEntity.getBody());
        Assertions.assertEquals("johndoe123", actualResponseEntity.getBody().credentials().username());
        Assertions.assertEquals("john.doe@example.com", actualResponseEntity.getBody().userInformation().contact().email());

        Mockito.verify(registerUserService, Mockito.times(1)).registerUser(registerUserRequestTO);

    }
}
