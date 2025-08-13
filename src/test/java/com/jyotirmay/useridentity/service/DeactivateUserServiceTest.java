package com.jyotirmay.useridentity.service;

import com.jyotirmay.useridentity.data.TestDataGenerator;
import com.jyotirmay.useridentity.dto.DeactivateUserResponseTO;
import com.jyotirmay.useridentity.entity.UserEntity;
import com.jyotirmay.useridentity.exception.UserNotFoundException;
import com.jyotirmay.useridentity.repository.UserIdentityRepository;
import com.jyotirmay.useridentity.service.impl.DeactivateUserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DeactivateUserServiceTest {

    @Mock
    private UserIdentityRepository userIdentityRepository;

    @InjectMocks
    private DeactivateUserServiceImpl deactivateUserService;

    @Test
    void givenValidEmail_whenDeactivateUserByEmailCalled_thenReturnsSuccessfulDeletionMessage() {
        String email = "john.doe@example.com";

        UserEntity userEntity = TestDataGenerator.buildUserEntity();
        mockUserRelationsActive(userEntity);

        Mockito.when(userIdentityRepository.findByEmailWithAllRelations(email, true)).thenReturn(Optional.of(userEntity));
        Mockito.when(userIdentityRepository.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);

        DeactivateUserResponseTO response = deactivateUserService.deactivateUserByEmail(email);

        Assertions.assertEquals("User with emailId john.doe@example.com deleted successfully", response.message());
        Assertions.assertFalse(userEntity.isActive());
        Assertions.assertFalse(userEntity.getAddress().isActive());
        Assertions.assertFalse(userEntity.getContact().isActive());
        Assertions.assertFalse(userEntity.getCredential().isActive());

        Mockito.verify(userIdentityRepository).save(userEntity);
    }

    @Test
    void givenInvalidEmail_whenDeactivateUserByEmailCalled_thenThrowsUserNotFoundException() {
        String email = "unknown@example.com";

        Mockito.when(userIdentityRepository.findByEmailWithAllRelations(email, true)).thenReturn(Optional.empty());

        UserNotFoundException exception = Assertions.assertThrows(UserNotFoundException.class,
                () -> deactivateUserService.deactivateUserByEmail(email));

        Assertions.assertTrue(exception.getMessage().contains("User with emailId unknown@example.com does not exist."));
    }

    @Test
    void givenValidUsername_whenDeactivateUserByUsernameCalled_thenReturnsSuccessfulDeletionMessage() {
        String username = "johndoe";

        UserEntity userEntity = TestDataGenerator.buildUserEntity();
        mockUserRelationsActive(userEntity);

        Mockito.when(userIdentityRepository.findByUsernameWithAllRelations(username, true)).thenReturn(Optional.of(userEntity));
        Mockito.when(userIdentityRepository.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);

        DeactivateUserResponseTO response = deactivateUserService.deactivateUserByUsername(username);

        Assertions.assertEquals("User with username johndoe deleted successfully", response.message());
        Assertions.assertFalse(userEntity.isActive());
        Assertions.assertFalse(userEntity.getAddress().isActive());
        Assertions.assertFalse(userEntity.getContact().isActive());
        Assertions.assertFalse(userEntity.getCredential().isActive());

        Mockito.verify(userIdentityRepository).save(userEntity);
    }

    @Test
    void givenInvalidUsername_whenDeactivateUserByUsernameCalled_thenThrowsUserNotFoundException() {
        String username = "unknownuser";

        Mockito.when(userIdentityRepository.findByUsernameWithAllRelations(username, true)).thenReturn(Optional.empty());

        UserNotFoundException exception = Assertions.assertThrows(UserNotFoundException.class,
                () -> deactivateUserService.deactivateUserByUsername(username));

        Assertions.assertTrue(exception.getMessage().contains("User with username unknownuser does not exist."));
    }

    // Helper method to set all related entities as active initially
    private void mockUserRelationsActive(UserEntity userEntity) {
        userEntity.setActive(true);
        userEntity.getAddress().setActive(true);
        userEntity.getContact().setActive(true);
        userEntity.getCredential().setActive(true);
    }
}
