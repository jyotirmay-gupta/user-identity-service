package com.jyotirmay.useridentity.controller;

import com.jyotirmay.useridentity.data.TestDataGenerator;
import com.jyotirmay.useridentity.dto.CredentialTO;
import com.jyotirmay.useridentity.dto.RegisterUserRequestTO;
import com.jyotirmay.useridentity.dto.RegisterUserResponseTO;
import com.jyotirmay.useridentity.dto.UserInformationTO;
import com.jyotirmay.useridentity.service.RegisterUserService;
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
class UserIdentityControllerRegisterUserTest {

    @InjectMocks
    private UserIdentityController userIdentityController;

    @Mock
    private RegisterUserService registerUserService;

    @Test
    void givenValidRegisterUserRequest_whenRegisterUserCalled_thenReturnsCreatedUserResponse() throws Exception {

        RegisterUserRequestTO registerUserRequestTO = TestDataGenerator.buildRegisterUserRequestTO();
        UserInformationTO userInformationTO = TestDataGenerator.buildUserInformationTO();
        CredentialTO credentialTO = new CredentialTO("johndoe123", "password");
        RegisterUserResponseTO registerUserResponseTO = new RegisterUserResponseTO(userInformationTO, credentialTO);

        Mockito.when(registerUserService.registerUser(registerUserRequestTO)).thenReturn(registerUserResponseTO);

        ResponseEntity<RegisterUserResponseTO> actualResponseEntity = userIdentityController.registerUser(registerUserRequestTO);

        Assertions.assertEquals(HttpStatus.CREATED, actualResponseEntity.getStatusCode());
        Assertions.assertNotNull(actualResponseEntity.getBody());
        Assertions.assertEquals("johndoe123", actualResponseEntity.getBody().credentials().username());
        Assertions.assertEquals("john.doe@example.com", actualResponseEntity.getBody().userInformation().contact().email());

        Mockito.verify(registerUserService, Mockito.times(1)).registerUser(registerUserRequestTO);

    }
}
