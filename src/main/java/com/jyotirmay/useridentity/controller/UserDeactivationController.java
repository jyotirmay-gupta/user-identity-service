package com.jyotirmay.useridentity.controller;

import com.jyotirmay.useridentity.dto.DeactivateUserResponseTO;
import com.jyotirmay.useridentity.enums.IdentifierType;
import com.jyotirmay.useridentity.enums.SourceType;
import com.jyotirmay.useridentity.service.DeactivateUserService;
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

    private final DeactivateUserService deactivateUserService;

    public UserDeactivationController(DeactivateUserService deactivateUserService) {
        this.deactivateUserService = deactivateUserService;
    }

    @DeleteMapping(params = "email")
    public ResponseEntity<DeactivateUserResponseTO> deactivateUserByEmailFromParam(@RequestParam(required = true, name = "email")
                                                                                 @NotBlank(message = "Email must not be blank")
                                                                                 @Email(message = "Invalid email format") String email) {
        logUserDeactivationRequest(SourceType.PARAM, IdentifierType.EMAIL, email);
        DeactivateUserResponseTO deactivateUserResponseTO = deactivateUserService.deactivateUserByEmailId(email);
        return new ResponseEntity<>(deactivateUserResponseTO, HttpStatus.OK);
    }

    @DeleteMapping(params = "username")
    public ResponseEntity<DeactivateUserResponseTO> deactivateUserByUsernameFromParam(@RequestParam(required = true)
                                                                              @NotBlank(message = "Username must not be blank") String username) {
        logUserDeactivationRequest(SourceType.PARAM, IdentifierType.USERNAME, username);
        DeactivateUserResponseTO deactivateUserResponseTO = deactivateUserService.deactivateUserByUsername(username);
        return new ResponseEntity<>(deactivateUserResponseTO, HttpStatus.OK);
    }

    @DeleteMapping(headers = {"User-Email"})
    public ResponseEntity<DeactivateUserResponseTO> deactivateUserByEmailFromHeader(@RequestHeader(name = "User-Email") @NotBlank(message = "Email must not be blank")
                                                                                  @Email(message = "Invalid email format") String email) {
        logUserDeactivationRequest(SourceType.HEADER, IdentifierType.EMAIL, email);
        DeactivateUserResponseTO deactivateUserResponseTO = deactivateUserService.deactivateUserByEmailId(email);
        return new ResponseEntity<>(deactivateUserResponseTO, HttpStatus.OK);
    }

    @DeleteMapping(headers = {"User-Name"})
    public ResponseEntity<DeactivateUserResponseTO> deactivateUserByUsernameFromHeader(@RequestHeader(name = "User-Name")
                                                                                   @NotBlank(message = "Username must not be blank") String username) {
        logUserDeactivationRequest(SourceType.HEADER, IdentifierType.USERNAME, username);
        DeactivateUserResponseTO deactivateUserResponseTO = deactivateUserService.deactivateUserByUsername(username);
        return new ResponseEntity<>(deactivateUserResponseTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/email/{email}")
    public ResponseEntity<DeactivateUserResponseTO> deactivateUserByEmailFromPathVariable(@PathVariable(required = true, name = "email") @NotBlank(message = "Email must not be blank")
                                                                            @Email(message = "Invalid email format") String email) {
        logUserDeactivationRequest(SourceType.PATH, IdentifierType.EMAIL, email);
        DeactivateUserResponseTO deactivateUserResponseTO = deactivateUserService.deactivateUserByEmailId(email);
        return new ResponseEntity<>(deactivateUserResponseTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/username/{username}")
    public ResponseEntity<DeactivateUserResponseTO> deactivateUserByUsernameFromPathVariable(@PathVariable(required = true, name = "username")
                                                                               @NotBlank(message = "Username must not be blank") String username) {
        logUserDeactivationRequest(SourceType.PATH, IdentifierType.USERNAME, username);
        DeactivateUserResponseTO deactivateUserResponseTO = deactivateUserService.deactivateUserByUsername(username);
        return new ResponseEntity<>(deactivateUserResponseTO, HttpStatus.OK);
    }

    private static void logUserDeactivationRequest(SourceType sourceType, IdentifierType identifierType, String identifier) {
        LOGGER.info("Received request to deactivate user by {} {} via {}", ()-> identifierType, () -> identifier, () -> sourceType);
    }
}
