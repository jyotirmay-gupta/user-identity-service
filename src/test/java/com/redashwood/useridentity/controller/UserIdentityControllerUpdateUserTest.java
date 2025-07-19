package com.redashwood.useridentity.controller;

import com.redashwood.useridentity.data.TestDataGenerator;
import com.redashwood.useridentity.dto.RegisterUserResponseTO;
import com.redashwood.useridentity.dto.UpdateUserRequestTO;
import com.redashwood.useridentity.dto.UpdateUserResponseTO;
import com.redashwood.useridentity.dto.UserInformationTO;
import com.redashwood.useridentity.service.UpdateUserService;
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
class UserIdentityControllerUpdateUserTest {

    @InjectMocks
    private UserIdentityController userIdentityController;

    @Mock
    private UpdateUserService updateUserService;

    @Test
    void givenValidUpdateUserRequest_whenUpdateUserByEmailCalled_thenReturnsUpdatedUserResponse() {

        UpdateUserRequestTO updateUserRequestTO = TestDataGenerator.buildUpdateUserRequestTO();
        UserInformationTO userInformationTO = TestDataGenerator.buildUserInformationTO();
        UpdateUserResponseTO updateUserResponseTO = new UpdateUserResponseTO(userInformationTO);

        Mockito.when(updateUserService.updateUserByEmailId(updateUserRequestTO, "john.doe@example.com")).thenReturn(updateUserResponseTO);

        ResponseEntity<UpdateUserResponseTO> actualResponseEntity = userIdentityController.updateUserByEmailId(updateUserRequestTO, "john.doe@example.com");

        Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        Assertions.assertNotNull(actualResponseEntity.getBody());
        Assertions.assertEquals("john.doe@example.com", actualResponseEntity.getBody().userInformation().contact().email());

        Mockito.verify(updateUserService, Mockito.times(1)).updateUserByEmailId(updateUserRequestTO, "john.doe@example.com");
    }

    @Test
    void givenValidUpdateUserRequest_whenUpdateUserByUsernameCalled_thenReturnsUpdatedUserResponse() {

        UpdateUserRequestTO updateUserRequestTO = TestDataGenerator.buildUpdateUserRequestTO();
        UserInformationTO userInformationTO = TestDataGenerator.buildUserInformationTO();
        UpdateUserResponseTO updateUserResponseTO = new UpdateUserResponseTO(userInformationTO);

        Mockito.when(updateUserService.updateUserByUsername(updateUserRequestTO, "johndoe123")).thenReturn(updateUserResponseTO);

        ResponseEntity<UpdateUserResponseTO> actualResponseEntity = userIdentityController.updateUserByUsername(updateUserRequestTO, "johndoe123");

        Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        Assertions.assertNotNull(actualResponseEntity.getBody());
        Assertions.assertEquals("john.doe@example.com", actualResponseEntity.getBody().userInformation().contact().email());

        Mockito.verify(updateUserService, Mockito.times(1)).updateUserByUsername(updateUserRequestTO, "johndoe123");
    }
}
