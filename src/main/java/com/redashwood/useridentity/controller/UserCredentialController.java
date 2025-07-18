package com.redashwood.useridentity.controller;

import com.redashwood.useridentity.dto.UpdateCredentialsRequestTO;
import com.redashwood.useridentity.dto.UpdateCredentialsResponseTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCredentialController {

    private static final Logger LOGGER = LogManager.getLogger(UserCredentialController.class);


    public ResponseEntity<UpdateCredentialsResponseTO> updateUserCredentials(@RequestBody UpdateCredentialsRequestTO updateCredentialsRequestTO){

        return null;
    }

}
