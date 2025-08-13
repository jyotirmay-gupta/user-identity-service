package com.jyotirmay.useridentity.controller;

import com.jyotirmay.useridentity.dto.UpdateCredentialRequestTO;
import com.jyotirmay.useridentity.dto.UpdateCredentialResponseTO;
import com.jyotirmay.useridentity.service.UpdateUserCredentialService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class UserCredentialControllerTest {

    @InjectMocks
    private UserCredentialController userCredentialController;

    @Mock
    private UpdateUserCredentialService updateUserCredentialService;

    @Test
    void givenValidCredentialRequest_whenUpdateUserCredentialsCalled_thenReturnsUpdatedCredentialResponse() {

        UpdateCredentialRequestTO updateCredentialRequestTO = new UpdateCredentialRequestTO("johndoe123", "password");
        UpdateCredentialResponseTO updateCredentialResponseTO = new UpdateCredentialResponseTO("Credential for user with username johndoe123 updated successfully");

        Mockito.when(updateUserCredentialService.updateUserCredential(updateCredentialRequestTO)).thenReturn(updateCredentialResponseTO);

        ResponseEntity<UpdateCredentialResponseTO> actualResponseEntity = userCredentialController.updateUserCredentials(updateCredentialRequestTO);

        Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        Assertions.assertNotNull(actualResponseEntity.getBody());
        Assertions.assertEquals("Credential for user with username johndoe123 updated successfully", actualResponseEntity.getBody().message());

        Mockito.verify(updateUserCredentialService, Mockito.times(1)).updateUserCredential(updateCredentialRequestTO);

    }
}
