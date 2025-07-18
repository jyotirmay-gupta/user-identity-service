package com.redashwood.useridentity.controller;

import com.redashwood.useridentity.dto.*;
import com.redashwood.useridentity.service.DeleteUserService;
import com.redashwood.useridentity.service.GetUserService;
import com.redashwood.useridentity.service.RegisterUserService;
import com.redashwood.useridentity.service.UpdateUserService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
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

    @PostMapping(value = "/user/identity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterUserResponseTO> registerUser(@Valid @RequestBody RegisterUserRequestTO registerUserRequestTO) {
        LOGGER.info("Received request to register a user: {} {} {}", registerUserRequestTO.firstName(), registerUserRequestTO.middleName(), registerUserRequestTO.lastName());
        RegisterUserResponseTO registerUserResponseTO = registerUserService.registerUser(registerUserRequestTO);
        return new ResponseEntity<>(registerUserResponseTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/user/identity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateUserResponseTO> updateUser(@Valid @RequestBody UpdateUserRequestTO updateUserRequestTO) {

        UpdateUserResponseTO updateUserResponseTO = updateUserService.updateUser(updateUserRequestTO);
        return new ResponseEntity<>(updateUserResponseTO, HttpStatus.OK);
    }

    @GetMapping(value = "/user/identity", params = "email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetUserResponseTO> getUserByEmailId(@RequestParam(required = true) String email) {

        GetUserResponseTO getUserResponseTO = getUserService.getUserByEmailId(email);
        return new ResponseEntity<>(getUserResponseTO, HttpStatus.OK);
    }

    @GetMapping(value = "/user/identity", params = "username", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetUserResponseTO> getUserByUsername(@RequestParam(required = true) String username) {

        GetUserResponseTO getUserResponseTO = getUserService.getUserByUsername(username);
        return new ResponseEntity<>(getUserResponseTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/user/identity", params = "email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeleteUserResponseTO> deleteUserByEmailId(@RequestParam(required = true) String email) {

        DeleteUserResponseTO deleteUserResponseTO = deleteUserService.deleteUserByEmailId(email);
        return new ResponseEntity<>(deleteUserResponseTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/user/identity", params = "username", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeleteUserResponseTO> deleteUserByUsername(@RequestParam(required = true) String username) {

        DeleteUserResponseTO deleteUserResponseTO = deleteUserService.deleteUserByUsername(username);
        return new ResponseEntity<>(deleteUserResponseTO, HttpStatus.OK);
    }

}
