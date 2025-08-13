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

import com.jyotirmay.useridentity.dto.DeleteUserResponseTO;
import com.jyotirmay.useridentity.dto.GetUserResponseTO;
import com.jyotirmay.useridentity.dto.RegisterUserRequestTO;
import com.jyotirmay.useridentity.dto.RegisterUserResponseTO;
import com.jyotirmay.useridentity.dto.UpdateUserRequestTO;
import com.jyotirmay.useridentity.dto.UpdateUserResponseTO;
import com.jyotirmay.useridentity.service.DeleteUserService;
import com.jyotirmay.useridentity.service.GetUserService;
import com.jyotirmay.useridentity.service.RegisterUserService;
import com.jyotirmay.useridentity.service.UpdateUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated  // Required to enable parameter-level validation
public class UserIdentityController {

    private static final Logger LOGGER = LogManager.getLogger(UserIdentityController.class);

    private final RegisterUserService registerUserService;

    private final UpdateUserService updateUserService;

    private final GetUserService getUserService;

    private final DeleteUserService deleteUserService;

    public UserIdentityController(RegisterUserService registerUserService, UpdateUserService updateUserService, GetUserService getUserService, DeleteUserService deleteUserService) {
        this.registerUserService = registerUserService;
        this.updateUserService = updateUserService;
        this.getUserService = getUserService;
        this.deleteUserService = deleteUserService;
    }

    @PostMapping(value = "/user/identity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept-Version=v1")
    public ResponseEntity<RegisterUserResponseTO> registerUser(@Valid @RequestBody RegisterUserRequestTO registerUserRequestTO) {
        LOGGER.info("Received request to register a user: {} {} {}", registerUserRequestTO.firstName(), registerUserRequestTO.middleName(), registerUserRequestTO.lastName());
        RegisterUserResponseTO registerUserResponseTO = registerUserService.registerUser(registerUserRequestTO);
        return new ResponseEntity<>(registerUserResponseTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/user/identity", params = "email", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept-Version=v1")
    public ResponseEntity<UpdateUserResponseTO> updateUserByEmailId(@Valid @RequestBody UpdateUserRequestTO updateUserRequestTO, @RequestParam(required = true, name = "email") @NotBlank(message = "Email must not be blank") @Email(message = "Invalid email format") String emailId) {
        LOGGER.info("Received request to update user {} {} {} by emailId {}", updateUserRequestTO.firstName(), updateUserRequestTO.middleName(), updateUserRequestTO.lastName(), emailId);
        UpdateUserResponseTO updateUserResponseTO = updateUserService.updateUserByEmailId(updateUserRequestTO, emailId);
        return new ResponseEntity<>(updateUserResponseTO, HttpStatus.OK);
    }

    @PutMapping(value = "/user/identity", params = "username", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept-Version=v1")
    public ResponseEntity<UpdateUserResponseTO> updateUserByUsername(@Valid @RequestBody UpdateUserRequestTO updateUserRequestTO, @RequestParam(required = true) @NotBlank(message = "Username must not be blank") String username) {
        LOGGER.info("Received request to update user {} {} {} by username {}", updateUserRequestTO.firstName(), updateUserRequestTO.middleName(), updateUserRequestTO.lastName(), username);
        UpdateUserResponseTO updateUserResponseTO = updateUserService.updateUserByUsername(updateUserRequestTO, username);
        return new ResponseEntity<>(updateUserResponseTO, HttpStatus.OK);
    }

    @GetMapping(value = "/user/identity", params = "email", produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept-Version=v1")
    public ResponseEntity<GetUserResponseTO> getUserByEmailId(@RequestParam(required = true, name = "email") @NotBlank(message = "Email must not be blank") @Email(message = "Invalid email format") String emailId) {
        LOGGER.info("Received request to fetch user by emailId {}", emailId);
        GetUserResponseTO getUserResponseTO = getUserService.getUserByEmailId(emailId);
        return new ResponseEntity<>(getUserResponseTO, HttpStatus.OK);
    }

    @GetMapping(value = "/user/identity", params = "username", produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept-Version=v1")
    public ResponseEntity<GetUserResponseTO> getUserByUsername(@RequestParam(required = true) @NotBlank(message = "Username must not be blank") String username) {
        LOGGER.info("Received request to get user by username {}", username);
        GetUserResponseTO getUserResponseTO = getUserService.getUserByUsername(username);
        return new ResponseEntity<>(getUserResponseTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/user/identity", params = "email", produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept-Version=v1")
    public ResponseEntity<DeleteUserResponseTO> deleteUserByEmailIdFromParam(@RequestParam(required = true, name = "email") @NotBlank(message = "Email must not be blank") @Email(message = "Invalid email format") String emailId) {
        LOGGER.info("Received request to deactivate user by emailId from param {}", emailId);
        DeleteUserResponseTO deleteUserResponseTO = deleteUserService.deleteUserByEmailId(emailId);
        return new ResponseEntity<>(deleteUserResponseTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/user/identity", params = "email", produces = MediaType.APPLICATION_JSON_VALUE, headers = {"Accept-Version=v1", "User-Email"})
    public ResponseEntity<DeleteUserResponseTO> deleteUserByEmailIdFromHeader(@RequestHeader(name = "User-Email") @NotBlank(message = "Email must not be blank") @Email(message = "Invalid email format") String emailId) {
        LOGGER.info("Received request to deactivate user by emailId from header {}", emailId);
        DeleteUserResponseTO deleteUserResponseTO = deleteUserService.deleteUserByEmailId(emailId);
        return new ResponseEntity<>(deleteUserResponseTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/user/identity", params = "username", produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept-Version=v1")
    public ResponseEntity<DeleteUserResponseTO> deleteUserByUsername(@RequestParam(required = true) @NotBlank(message = "Username must not be blank") String username) {
        LOGGER.info("Received request to deactivate user by username {}", username);
        DeleteUserResponseTO deleteUserResponseTO = deleteUserService.deleteUserByUsername(username);
        return new ResponseEntity<>(deleteUserResponseTO, HttpStatus.OK);
    }

}
