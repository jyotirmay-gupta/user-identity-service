package com.jyotirmay.useridentity.controller;

import com.jyotirmay.useridentity.dto.DeleteUserResponseTO;
import com.jyotirmay.useridentity.enums.IdentifierType;
import com.jyotirmay.useridentity.enums.SourceType;
import com.jyotirmay.useridentity.service.DeleteUserService;
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
@Validated
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept-Version=v1")
public class UserDeactivationController {

    private static final Logger LOGGER = LogManager.getLogger(UserDeactivationController.class);

    private final DeleteUserService deleteUserService;

    public UserDeactivationController(DeleteUserService deleteUserService) {
        this.deleteUserService = deleteUserService;
    }

    @DeleteMapping(params = "email")
    public ResponseEntity<DeleteUserResponseTO> deactivateUserByEmailFromParam(@RequestParam(required = true, name = "email")
                                                                                 @NotBlank(message = "Email must not be blank")
                                                                                 @Email(message = "Invalid email format") String email) {
        logUserDeactivationRequest(SourceType.PARAM, IdentifierType.EMAIL, email);
        DeleteUserResponseTO deleteUserResponseTO = deleteUserService.deleteUserByEmailId(email);
        return new ResponseEntity<>(deleteUserResponseTO, HttpStatus.OK);
    }

    @DeleteMapping(params = "username")
    public ResponseEntity<DeleteUserResponseTO> deactivateUserByUsernameFromParam(@RequestParam(required = true)
                                                                              @NotBlank(message = "Username must not be blank") String username) {
        logUserDeactivationRequest(SourceType.PARAM, IdentifierType.USERNAME, username);
        DeleteUserResponseTO deleteUserResponseTO = deleteUserService.deleteUserByUsername(username);
        return new ResponseEntity<>(deleteUserResponseTO, HttpStatus.OK);
    }

    @DeleteMapping(headers = {"User-Email"})
    public ResponseEntity<DeleteUserResponseTO> deactivateUserByEmailFromHeader(@RequestHeader(name = "User-Email") @NotBlank(message = "Email must not be blank")
                                                                                  @Email(message = "Invalid email format") String email) {
        logUserDeactivationRequest(SourceType.HEADER, IdentifierType.EMAIL, email);
        DeleteUserResponseTO deleteUserResponseTO = deleteUserService.deleteUserByEmailId(email);
        return new ResponseEntity<>(deleteUserResponseTO, HttpStatus.OK);
    }

    @DeleteMapping(headers = {"User-Name"})
    public ResponseEntity<DeleteUserResponseTO> deactivateUserByUsernameFromHeader(@RequestHeader(name = "User-Name")
                                                                                   @NotBlank(message = "Username must not be blank") String username) {
        logUserDeactivationRequest(SourceType.HEADER, IdentifierType.USERNAME, username);
        DeleteUserResponseTO deleteUserResponseTO = deleteUserService.deleteUserByUsername(username);
        return new ResponseEntity<>(deleteUserResponseTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/email/{email}")
    public ResponseEntity<DeleteUserResponseTO> deactivateUserByEmailFromPathVariable(@PathVariable(required = true, name = "email") @NotBlank(message = "Email must not be blank")
                                                                            @Email(message = "Invalid email format") String email) {
        logUserDeactivationRequest(SourceType.PATH, IdentifierType.EMAIL, email);
        DeleteUserResponseTO deleteUserResponseTO = deleteUserService.deleteUserByEmailId(email);
        return new ResponseEntity<>(deleteUserResponseTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/username/{username}")
    public ResponseEntity<DeleteUserResponseTO> deactivateUserByUsernameFromPathVariable(@PathVariable(required = true, name = "username")
                                                                               @NotBlank(message = "Username must not be blank") String username) {
        logUserDeactivationRequest(SourceType.PATH, IdentifierType.USERNAME, username);
        DeleteUserResponseTO deleteUserResponseTO = deleteUserService.deleteUserByUsername(username);
        return new ResponseEntity<>(deleteUserResponseTO, HttpStatus.OK);
    }

    private static void logUserDeactivationRequest(SourceType sourceType, IdentifierType identifierType, String identifier) {
        LOGGER.info("Received request to deactivate user by {} {} via {}", ()-> identifierType, () -> identifier, () -> sourceType);
    }
}
