package com.redashwood.useridentity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redashwood.useridentity.data.TestDataGenerator;
import com.redashwood.useridentity.dto.*;
import com.redashwood.useridentity.dto.enums.GenderEnum;
import com.redashwood.useridentity.entity.UserAddressEntity;
import com.redashwood.useridentity.entity.UserContactEntity;
import com.redashwood.useridentity.entity.UserCredentialEntity;
import com.redashwood.useridentity.entity.UserEntity;
import com.redashwood.useridentity.repository.UserIdentityRepository;
import com.redashwood.useridentity.service.RegisterUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

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
