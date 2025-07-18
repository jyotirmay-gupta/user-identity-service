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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    public ResponseEntity<RegisterUserResponseTO> registerUser(@Valid @RequestBody RegisterUserRequestTO registerUserRequestTO) {
        LOGGER.info("Received request to register a user: {} {} {}", registerUserRequestTO.firstName(), registerUserRequestTO.middleName(), registerUserRequestTO.lastName());
        RegisterUserResponseTO registerUserResponseTO = registerUserService.registerUser(registerUserRequestTO);
        return new ResponseEntity<>(registerUserResponseTO, HttpStatus.CREATED);
    }

    public ResponseEntity<UpdateUserResponseTO> updateUser(@Valid @RequestBody UpdateUserRequestTO updateUserRequestTO) {

        UpdateUserResponseTO updateUserResponseTO = updateUserService.updateUser(updateUserRequestTO);
        return new ResponseEntity<>(updateUserResponseTO, HttpStatus.OK);
    }

    public ResponseEntity<GetUserResponseTO> getUserByEmailId(@Valid @RequestBody GetUserByEmailRequestTO getUserByEmailRequestTO) {

        GetUserResponseTO getUserResponseTO = getUserService.getUserByEmailId(getUserByEmailRequestTO);
        return new ResponseEntity<>(getUserResponseTO, HttpStatus.OK);
    }

    public ResponseEntity<GetUserResponseTO> getUserByUsername(@Valid @RequestBody GetUserByUsernameRequestTO getUserByUsernameRequestTO) {

        GetUserResponseTO getUserResponseTO = getUserService.getUserByUsername(getUserByUsernameRequestTO);
        return new ResponseEntity<>(getUserResponseTO, HttpStatus.OK);
    }

    public ResponseEntity<DeleteUserResponseTO> deleteUserByEmailId(@Valid @RequestBody DeleteUserByEmailRequestTO deleteUserByEmailRequestTO) {

        DeleteUserResponseTO deleteUserResponseTO = deleteUserService.deleteUserByEmailId(deleteUserByEmailRequestTO);
        return new ResponseEntity<>(deleteUserResponseTO, HttpStatus.OK);
    }

    public ResponseEntity<DeleteUserResponseTO> deleteUserByUsername(@Valid @RequestBody DeleteUserByUsernameRequestTO deleteUserByUsernameRequestTO) {

        DeleteUserResponseTO deleteUserResponseTO = deleteUserService.deleteUserByUsername(deleteUserByUsernameRequestTO);
        return new ResponseEntity<>(deleteUserResponseTO, HttpStatus.OK);

    }

}
