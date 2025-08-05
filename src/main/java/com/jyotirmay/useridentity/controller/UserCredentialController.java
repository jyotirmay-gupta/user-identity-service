package com.jyotirmay.useridentity.controller;

import com.jyotirmay.useridentity.dto.UpdateCredentialRequestTO;
import com.jyotirmay.useridentity.dto.UpdateCredentialResponseTO;
import com.jyotirmay.useridentity.service.UpdateUserCredentialService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCredentialController {

    private static final Logger LOGGER = LogManager.getLogger(UserCredentialController.class);

    private final UpdateUserCredentialService updateUserCredentialService;

    public UserCredentialController(UpdateUserCredentialService updateUserCredentialService) {
        this.updateUserCredentialService = updateUserCredentialService;
    }

    @PutMapping(value = "/user/credential", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept-Version=v1")
    public ResponseEntity<UpdateCredentialResponseTO> updateUserCredentials(@Valid @RequestBody UpdateCredentialRequestTO updateCredentialRequestTO) {
        LOGGER.info("Received request to update credentials for {}", updateCredentialRequestTO.username());
        UpdateCredentialResponseTO updateCredentialResponseTO = updateUserCredentialService.updateUserCredential(updateCredentialRequestTO);
        return new ResponseEntity<>(updateCredentialResponseTO, HttpStatus.OK);
    }

}
