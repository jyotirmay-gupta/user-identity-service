package com.jyotirmay.useridentity.controller;

import com.jyotirmay.useridentity.data.TestDataGenerator;
import com.jyotirmay.useridentity.dto.GetUserResponseTO;
import com.jyotirmay.useridentity.dto.UserInformationTO;
import com.jyotirmay.useridentity.service.GetUserService;
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
public class UserIdentityControllerGetUserTest {

    @InjectMocks
    private UserIdentityController userIdentityController;

    @Mock
    private GetUserService getUserService;

    @Test
    void givenValidEmail_whenGetUserByEmailCalled_thenReturnsUserResponse() {

        UserInformationTO userInformationTO = TestDataGenerator.buildUserInformationTO();
        GetUserResponseTO getUserResponseTO = new GetUserResponseTO(userInformationTO);

        Mockito.when(getUserService.getUserByEmailId("john.doe@example.com")).thenReturn(getUserResponseTO);

        ResponseEntity<GetUserResponseTO> actualResponseEntity = userIdentityController.getUserByEmailId("john.doe@example.com");

        Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        Assertions.assertNotNull(actualResponseEntity.getBody());
        Assertions.assertEquals("john.doe@example.com", actualResponseEntity.getBody().userInformation().contact().email());

        Mockito.verify(getUserService, Mockito.times(1)).getUserByEmailId("john.doe@example.com");
    }

    @Test
    void givenValidEmail_whenGetUserByUsernameCalled_thenReturnsUserResponse() {

        UserInformationTO userInformationTO = TestDataGenerator.buildUserInformationTO();
        GetUserResponseTO getUserResponseTO = new GetUserResponseTO(userInformationTO);

        Mockito.when(getUserService.getUserByUsername("johndoe123")).thenReturn(getUserResponseTO);

        ResponseEntity<GetUserResponseTO> actualResponseEntity = userIdentityController.getUserByUsername("johndoe123");

        Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        Assertions.assertNotNull(actualResponseEntity.getBody());
        Assertions.assertEquals("john.doe@example.com", actualResponseEntity.getBody().userInformation().contact().email());

        Mockito.verify(getUserService, Mockito.times(1)).getUserByUsername("johndoe123");
    }

}
