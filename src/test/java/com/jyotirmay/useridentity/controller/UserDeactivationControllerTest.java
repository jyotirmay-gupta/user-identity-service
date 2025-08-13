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
import com.jyotirmay.useridentity.dto.GetUserResponseTO;
import com.jyotirmay.useridentity.service.DeactivateUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
class UserDeactivationControllerTest {

    @InjectMocks
    private UserDeactivationController userDeactivationController;

    @Mock
    private DeactivateUserService deactivateUserService;

    @Test
    @DisplayName("Deactivate user by email from request parameter should return successful deletion response")
    void givenValidEmail_whenDeleteUserByEmailFromParamCalled_thenReturnsDeletionConfirmation() {

        testDeactivateUserByEmail(email -> userDeactivationController.deactivateUserByEmailFromParam(email));
    }

    @Test
    @DisplayName("Deactivate user by email from header should return successful deletion response")
    void givenValidEmail_whenDeleteUserByEmailFromHeaderCalled_thenReturnsDeletionConfirmation() {

        testDeactivateUserByEmail(email -> userDeactivationController.deactivateUserByEmailFromHeader(email));
    }

    @Test
    @DisplayName("Deactivate user by email from path variable should return successful deletion response")
    void givenValidEmail_whenDeleteUserByEmailFromPathVariableCalled_thenReturnsDeletionConfirmation() {

        testDeactivateUserByEmail(email -> userDeactivationController.deactivateUserByEmailFromPathVariable(email));
    }

    private void testDeactivateUserByEmail(Function<String, ResponseEntity<DeactivateUserResponseTO>> controllerMethod) {
        String email = "john.doe@example.com";
        DeactivateUserResponseTO deactivateUserResponseTO = new DeactivateUserResponseTO("User with emailId john.doe@example.com deleted successfully");
        Mockito.when(deactivateUserService.deactivateUserByEmail(email)).thenReturn(deactivateUserResponseTO);

        ResponseEntity<DeactivateUserResponseTO> actualResponseEntity = controllerMethod.apply(email);

        Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        Assertions.assertNotNull(actualResponseEntity.getBody());
        Assertions.assertEquals("User with emailId john.doe@example.com deleted successfully", actualResponseEntity.getBody().message());

        Mockito.verify(deactivateUserService, Mockito.times(1)).deactivateUserByEmail(email);
    }

    @Test
    @DisplayName("Deactivate user by username from request parameter should return successful deletion response")
    void givenValidEmail_whenDeleteUserByUsernameFromParamCalled_thenReturnsDeletionConfirmation() {

        testDeactivateUserByUsername(username -> userDeactivationController.deactivateUserByUsernameFromParam(username));
    }

    @Test
    @DisplayName("Deactivate user by username from header should return successful deletion response")
    void givenValidEmail_whenDeleteUserByUsernameFromHeaderCalled_thenReturnsDeletionConfirmation() {

        testDeactivateUserByUsername(username -> userDeactivationController.deactivateUserByUsernameFromHeader(username));
    }

    @Test
    @DisplayName("Deactivate user by username from path variable should return successful deletion response")
    void givenValidEmail_whenDeleteUserByUsernameFromPathVariableCalled_thenReturnsDeletionConfirmation() {

        testDeactivateUserByUsername(username -> userDeactivationController.deactivateUserByUsernameFromPathVariable(username));
    }

    private void testDeactivateUserByUsername(Function<String, ResponseEntity<DeactivateUserResponseTO>> controllerMethod) {
        String username = "johndoe123";
        DeactivateUserResponseTO deactivateUserResponseTO = new DeactivateUserResponseTO("User with username johndoe123 deleted successfully");
        Mockito.when(deactivateUserService.deactivateUserByUsername(username)).thenReturn(deactivateUserResponseTO);

        ResponseEntity<DeactivateUserResponseTO> actualResponseEntity = controllerMethod.apply(username);

        Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        Assertions.assertNotNull(actualResponseEntity.getBody());
        Assertions.assertEquals("User with username johndoe123 deleted successfully", actualResponseEntity.getBody().message());

        Mockito.verify(deactivateUserService, Mockito.times(1)).deactivateUserByUsername(username);
    }
}
