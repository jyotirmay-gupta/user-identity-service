package com.jyotirmay.useridentity.controller;

import com.jyotirmay.useridentity.dto.GetUserResponseTO;
import com.jyotirmay.useridentity.enums.IdentifierType;
import com.jyotirmay.useridentity.enums.SourceType;
import com.jyotirmay.useridentity.service.GetUserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept-Version=v1")
public class UserQueryController {

    private static final Logger LOGGER = LogManager.getLogger(UserQueryController.class);

    private final GetUserService getUserService;

    public UserQueryController(GetUserService getUserService) {
        this.getUserService = getUserService;
    }

    @GetMapping(params = "email")
    public ResponseEntity<GetUserResponseTO> getUserByEmailFromParam(@RequestParam(required = true, name = "email") @NotBlank(message = "Email must not be blank")
                                                                         @Email(message = "Invalid email format") String email) {
        logUserQueryInformation(SourceType.PARAM, IdentifierType.EMAIL, email);
        GetUserResponseTO getUserResponseTO = getUserService.getUserByEmailId(email);
        return ResponseEntity.ok(getUserResponseTO);
    }

    @GetMapping(params = "username")
    public ResponseEntity<GetUserResponseTO> getUserByUsernameFromParam(@RequestParam(required = true) @NotBlank(message = "Username must not be blank") String username) {
        logUserQueryInformation(SourceType.PARAM, IdentifierType.USERNAME, username);
        GetUserResponseTO getUserResponseTO = getUserService.getUserByUsername(username);
        return ResponseEntity.ok(getUserResponseTO);
    }

    @GetMapping(headers = {"User-Email"})
    public ResponseEntity<GetUserResponseTO> getUserByEmailIdFromHeader(@RequestHeader(name = "User-Email") @NotBlank(message = "Email must not be blank")
                                                                            @Email(message = "Invalid email format") String email) {
        logUserQueryInformation(SourceType.HEADER, IdentifierType.EMAIL, email);
        GetUserResponseTO getUserResponseTO = getUserService.getUserByEmailId(email);
        return ResponseEntity.ok(getUserResponseTO);
    }

    @GetMapping(headers = {"User-Name"})
    public ResponseEntity<GetUserResponseTO> getUserByUsernameFromHeader(@RequestHeader(name = "User-Name")  @NotBlank(message = "Username must not be blank") String username) {
        logUserQueryInformation(SourceType.HEADER, IdentifierType.USERNAME, username);
        GetUserResponseTO getUserResponseTO = getUserService.getUserByUsername(username);
        return ResponseEntity.ok(getUserResponseTO);
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<GetUserResponseTO> getUserByEmailIdFromPathVariable(@PathVariable(required = true, name = "email")
                                                                                  @NotBlank(message = "Email must not be blank") @Email(message = "Invalid email format")
                                                                                  String email) {
        logUserQueryInformation(SourceType.PATH, IdentifierType.EMAIL, email);
        GetUserResponseTO getUserResponseTO = getUserService.getUserByEmailId(email);
        return ResponseEntity.ok(getUserResponseTO);
    }

    @GetMapping(value = "/username/{username}")
    public ResponseEntity<GetUserResponseTO> getUserByUsernameFromPathVariable(@PathVariable(required = true, name = "username")
                                                                                   @NotBlank(message = "Username must not be blank") String username) {
        logUserQueryInformation(SourceType.PATH, IdentifierType.USERNAME, username);
        GetUserResponseTO getUserResponseTO = getUserService.getUserByUsername(username);
        return ResponseEntity.ok(getUserResponseTO);
    }

    private static void logUserQueryInformation(SourceType sourceType, IdentifierType identifierType, String identifier) {
        LOGGER.info("Received request to get user by {} {} via {}", ()-> identifierType, () -> identifier, () -> sourceType);
    }
}
