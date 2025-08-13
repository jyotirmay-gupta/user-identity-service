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

import com.jyotirmay.useridentity.dto.DeactivateUserResponseTO;
import com.jyotirmay.useridentity.service.DeactivateUserService;
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
class UserIdentityControllerDeleteUserTest {

    @InjectMocks
    private UserDeactivationController userDeactivationController;

    @Mock
    private DeactivateUserService deactivateUserService;

    @Test
    void givenValidEmail_whenDeleteUserByEmailFromParamCalled_thenReturnsDeletionConfirmation() {

        DeactivateUserResponseTO deactivateUserResponseTO = new DeactivateUserResponseTO("User with emailId john.doe@example.com deleted successfully");
        Mockito.when(deactivateUserService.deactivateUserByEmailId("john.doe@example.com")).thenReturn(deactivateUserResponseTO);

        ResponseEntity<DeactivateUserResponseTO> actualResponseEntity = userDeactivationController.deactivateUserByEmailFromParam("john.doe@example.com");

        Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        Assertions.assertNotNull(actualResponseEntity.getBody());
        Assertions.assertEquals("User with emailId john.doe@example.com deleted successfully", actualResponseEntity.getBody().message());

        Mockito.verify(deactivateUserService, Mockito.times(1)).deactivateUserByEmailId("john.doe@example.com");
    }

    @Test
    void givenValidEmail_whenDeleteUserByUsernameCalled_thenReturnsDeletionConfirmation() {

        DeactivateUserResponseTO deactivateUserResponseTO = new DeactivateUserResponseTO("User with username johndoe123 deleted successfully");
        Mockito.when(deactivateUserService.deactivateUserByUsername("johndoe123")).thenReturn(deactivateUserResponseTO);

        ResponseEntity<DeactivateUserResponseTO> actualResponseEntity = userDeactivationController.deactivateUserByUsernameFromParam("johndoe123");

        Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        Assertions.assertNotNull(actualResponseEntity.getBody());
        Assertions.assertEquals("User with username johndoe123 deleted successfully", actualResponseEntity.getBody().message());

        Mockito.verify(deactivateUserService, Mockito.times(1)).deactivateUserByUsername("johndoe123");
    }


}
