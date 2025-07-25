package com.redashwood.useridentity.controller;

import com.redashwood.useridentity.dto.DeleteUserResponseTO;
import com.redashwood.useridentity.service.DeleteUserService;
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
class UserIdentityControllerDeleteUserTest {

    @InjectMocks
    private UserIdentityController userIdentityController;

    @Mock
    private DeleteUserService deleteUserService;

    @Test
    void givenValidEmail_whenDeleteUserByEmailCalled_thenReturnsDeletionConfirmation() {

        DeleteUserResponseTO deleteUserResponseTO = new DeleteUserResponseTO("User with emailId john.doe@example.com deleted successfully");
        Mockito.when(deleteUserService.deleteUserByEmailId("john.doe@example.com")).thenReturn(deleteUserResponseTO);

        ResponseEntity<DeleteUserResponseTO> actualResponseEntity = userIdentityController.deleteUserByEmailId("john.doe@example.com");

        Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        Assertions.assertNotNull(actualResponseEntity.getBody());
        Assertions.assertEquals("User with emailId john.doe@example.com deleted successfully", actualResponseEntity.getBody().message());

        Mockito.verify(deleteUserService, Mockito.times(1)).deleteUserByEmailId("john.doe@example.com");
    }

    @Test
    void givenValidEmail_whenDeleteUserByUsernameCalled_thenReturnsDeletionConfirmation() {

        DeleteUserResponseTO deleteUserResponseTO = new DeleteUserResponseTO("User with username johndoe123 deleted successfully");
        Mockito.when(deleteUserService.deleteUserByUsername("johndoe123")).thenReturn(deleteUserResponseTO);

        ResponseEntity<DeleteUserResponseTO> actualResponseEntity = userIdentityController.deleteUserByUsername("johndoe123");

        Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        Assertions.assertNotNull(actualResponseEntity.getBody());
        Assertions.assertEquals("User with username johndoe123 deleted successfully", actualResponseEntity.getBody().message());

        Mockito.verify(deleteUserService, Mockito.times(1)).deleteUserByUsername("johndoe123");
    }


}
