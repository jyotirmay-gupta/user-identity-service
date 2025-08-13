package com.jyotirmay.useridentity.controller;

import com.jyotirmay.useridentity.dto.RegisterUserRequestTO;
import com.jyotirmay.useridentity.dto.RegisterUserResponseTO;
import com.jyotirmay.useridentity.service.RegisterUserService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept-Version=v1")
public class UserRegistrationController {

    private static final Logger LOGGER = LogManager.getLogger(UserRegistrationController.class);

    private final RegisterUserService registerUserService;

    public UserRegistrationController(RegisterUserService registerUserService) {
        this.registerUserService = registerUserService;
    }

    @PostMapping
    public ResponseEntity<RegisterUserResponseTO> registerUser(@Valid @RequestBody RegisterUserRequestTO registerUserRequestTO) {
        LOGGER.info("Received request to register a user: {} {} {}", () -> registerUserRequestTO.firstName(),
                () -> registerUserRequestTO.middleName(), () -> registerUserRequestTO.lastName());
        RegisterUserResponseTO registerUserResponseTO = registerUserService.registerUser(registerUserRequestTO);
        return new ResponseEntity<>(registerUserResponseTO, HttpStatus.CREATED);
    }

}
