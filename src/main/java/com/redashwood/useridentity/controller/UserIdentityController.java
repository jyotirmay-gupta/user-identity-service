package com.redashwood.useridentity.controller;

import com.redashwood.useridentity.dto.*;
import com.redashwood.useridentity.service.DeleteUserService;
import com.redashwood.useridentity.service.GetUserService;
import com.redashwood.useridentity.service.RegisterUserService;
import com.redashwood.useridentity.service.UpdateUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<DeleteUserResponseTO> deleteUserByEmailId(@RequestParam(required = true, name = "email") @NotBlank(message = "Email must not be blank") @Email(message = "Invalid email format") String emailId) {
        LOGGER.info("Received request to deactivate user by emailId {}", emailId);
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
