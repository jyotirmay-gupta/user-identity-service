package com.jyotirmay.useridentity.controller;

import com.jyotirmay.useridentity.dto.UpdateUserRequestTO;
import com.jyotirmay.useridentity.dto.UpdateUserResponseTO;
import com.jyotirmay.useridentity.enums.IdentifierType;
import com.jyotirmay.useridentity.enums.SourceType;
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
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for updating user details.
 * Supports updating by email or username via request parameters, headers, or path variables.
 * API versioning is enforced via the Accept-Version header.
 */
@RestController
@Validated
@RequestMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept-Version=v1")
public class UserUpdateController {

    private static final Logger LOGGER = LogManager.getLogger(UserUpdateController.class);

    private final UpdateUserService updateUserService;

    /**
     * Constructor to initialize the service dependency.
     * @param updateUserService service to handle user update logic
     */
    public UserUpdateController(UpdateUserService updateUserService) {
        this.updateUserService = updateUserService;
    }

    /**
     * Update user by email from query parameter.
     */
    @PutMapping(params = "email")
    public ResponseEntity<UpdateUserResponseTO> updateUserByEmailFromParam(@Valid @RequestBody UpdateUserRequestTO updateUserRequestTO,
                                                                             @RequestParam(required = true, name = "email") @NotBlank(message = "Email must not be blank")
                                                                             @Email(message = "Invalid email format") String email) {
        logUserRequestInformation(updateUserRequestTO, SourceType.PARAM, IdentifierType.EMAIL, email);
        UpdateUserResponseTO updateUserResponseTO = updateUserService.updateUserByEmailId(updateUserRequestTO, email);
        return ResponseEntity.ok(updateUserResponseTO);
    }

    /**
     * Update user by username from query parameter.
     */
    @PutMapping(params = "username")
    public ResponseEntity<UpdateUserResponseTO> updateUserByUsernameFromParam(@Valid @RequestBody UpdateUserRequestTO updateUserRequestTO,
                                                                              @RequestParam(required = true) @NotBlank(message = "Username must not be blank") String username) {
        logUserRequestInformation(updateUserRequestTO, SourceType.PARAM, IdentifierType.USERNAME, username);
        UpdateUserResponseTO updateUserResponseTO = updateUserService.updateUserByUsername(updateUserRequestTO, username);
        return ResponseEntity.ok(updateUserResponseTO);
    }

    @PutMapping(headers = {"User-Email"})
    public ResponseEntity<UpdateUserResponseTO> updateUserByEmailFromHeader(@Valid @RequestBody UpdateUserRequestTO updateUserRequestTO,
                                                                              @RequestHeader(name = "User-Email") @NotBlank(message = "Email must not be blank")
                                                                              @Email(message = "Invalid email format") String email) {
        logUserRequestInformation(updateUserRequestTO, SourceType.HEADER, IdentifierType.EMAIL, email);
        UpdateUserResponseTO updateUserResponseTO = updateUserService.updateUserByEmailId(updateUserRequestTO, email);
        return ResponseEntity.ok(updateUserResponseTO);
    }

    /**
     * Update user by email from header.
     */
    @PutMapping(headers = {"User-Name"})
    public ResponseEntity<UpdateUserResponseTO> updateUserByUsernameFromHeader(@Valid @RequestBody UpdateUserRequestTO updateUserRequestTO,
                                                                               @RequestHeader(name = "User-Name") @NotBlank(message = "Username must not be blank") String username) {
        logUserRequestInformation(updateUserRequestTO, SourceType.HEADER, IdentifierType.USERNAME, username);
        UpdateUserResponseTO updateUserResponseTO = updateUserService.updateUserByUsername(updateUserRequestTO, username);
        return ResponseEntity.ok(updateUserResponseTO);
    }

    /**
     * Update user by email from path variable.
     */
    @PutMapping(value = "/email/{email}")
    public ResponseEntity<UpdateUserResponseTO> updateUserByEmailFromPathVariable(@Valid @RequestBody UpdateUserRequestTO updateUserRequestTO,
                                                                             @PathVariable(required = true, name = "email") @NotBlank(message = "Email must not be blank")
                                                                             @Email(message = "Invalid email format") String email) {
        logUserRequestInformation(updateUserRequestTO, SourceType.PATH, IdentifierType.EMAIL, email);
        UpdateUserResponseTO updateUserResponseTO = updateUserService.updateUserByEmailId(updateUserRequestTO, email);
        return ResponseEntity.ok(updateUserResponseTO);
    }

    /**
     * Update user by username from path variable.
     */
    @PutMapping(value = "/username/{username}")
    public ResponseEntity<UpdateUserResponseTO> updateUserByUsernameFromPathVariable(@Valid @RequestBody UpdateUserRequestTO updateUserRequestTO,
                                                                              @PathVariable(required = true, name = "username") @NotBlank(message = "Username must not be blank") String username) {
        logUserRequestInformation(updateUserRequestTO, SourceType.PATH, IdentifierType.USERNAME, username);
        UpdateUserResponseTO updateUserResponseTO = updateUserService.updateUserByUsername(updateUserRequestTO, username);
        return ResponseEntity.ok(updateUserResponseTO);
    }

    private static void logUserRequestInformation(UpdateUserRequestTO updateUserRequestTO, SourceType sourceType, IdentifierType identifierType, String identifier) {
        LOGGER.info("Received request to update user {} {} {} by {} {} via {}", () -> updateUserRequestTO.firstName(),
                () -> updateUserRequestTO.middleName(), () -> updateUserRequestTO.lastName(), () -> identifierType, () -> identifier, () -> sourceType);
    }


}
