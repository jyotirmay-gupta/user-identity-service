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

import com.jyotirmay.useridentity.dto.UpdateCredentialRequestTO;
import com.jyotirmay.useridentity.dto.UpdateCredentialResponseTO;
import com.jyotirmay.useridentity.service.UpdateUserCredentialService;
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
public class UserCredentialControllerTest {

    @InjectMocks
    private UserCredentialController userCredentialController;

    @Mock
    private UpdateUserCredentialService updateUserCredentialService;

    @Test
    void givenValidCredentialRequest_whenUpdateUserCredentialsCalled_thenReturnsUpdatedCredentialResponse() {

        UpdateCredentialRequestTO updateCredentialRequestTO = new UpdateCredentialRequestTO("johndoe123", "password");
        UpdateCredentialResponseTO updateCredentialResponseTO = new UpdateCredentialResponseTO("Credential for user with username johndoe123 updated successfully");

        Mockito.when(updateUserCredentialService.updateUserCredential(updateCredentialRequestTO)).thenReturn(updateCredentialResponseTO);

        ResponseEntity<UpdateCredentialResponseTO> actualResponseEntity = userCredentialController.updateUserCredentials(updateCredentialRequestTO);

        Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        Assertions.assertNotNull(actualResponseEntity.getBody());
        Assertions.assertEquals("Credential for user with username johndoe123 updated successfully", actualResponseEntity.getBody().message());

        Mockito.verify(updateUserCredentialService, Mockito.times(1)).updateUserCredential(updateCredentialRequestTO);

    }
}
