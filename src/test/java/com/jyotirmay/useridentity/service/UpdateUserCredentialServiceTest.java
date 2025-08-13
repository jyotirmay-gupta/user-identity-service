package com.jyotirmay.useridentity.service;

import com.jyotirmay.useridentity.data.TestDataGenerator;
import com.jyotirmay.useridentity.dto.UpdateCredentialRequestTO;
import com.jyotirmay.useridentity.dto.UpdateCredentialResponseTO;
import com.jyotirmay.useridentity.entity.UserEntity;
import com.jyotirmay.useridentity.exception.UserNotFoundException;
import com.jyotirmay.useridentity.repository.UserIdentityRepository;
import com.jyotirmay.useridentity.service.impl.UpdateUserCredentialServiceImpl;
import com.jyotirmay.useridentity.util.IdentityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UpdateUserCredentialServiceTest {

    @Mock
    private UserIdentityRepository userIdentityRepository;

    @Mock
    private IdentityUtils identityUtils;

    @InjectMocks
    private UpdateUserCredentialServiceImpl updateUserCredentialService;

    @Test
    @DisplayName("Given valid username and password, when updateUserCredential is called, then update password and return success message")
    void givenValidUsernameAndPassword_whenUpdateUserCredential_thenReturnSuccessResponse() {
        // given
        String username = "johndoe123";
        String rawPassword = "NewP@ssw0rd!";
        String encryptedPassword = "encryptedPassword";

        UpdateCredentialRequestTO requestTO = new UpdateCredentialRequestTO(username, rawPassword);
        UserEntity userEntity = TestDataGenerator.buildUserEntity();

        Mockito.when(userIdentityRepository.findByUsernameWithAllRelations(username, true))
                .thenReturn(Optional.of(userEntity));
        Mockito.when(identityUtils.encryptPassword(rawPassword)).thenReturn(encryptedPassword);

        // when
        UpdateCredentialResponseTO response = updateUserCredentialService.updateUserCredential(requestTO);

        // then
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Credential for user with username johndoe123 updated successfully", response.message());
        Assertions.assertEquals(encryptedPassword, userEntity.getCredential().getPassword());

        Mockito.verify(userIdentityRepository).save(userEntity);
        Mockito.verify(identityUtils).encryptPassword(rawPassword);
    }

    @Test
    @DisplayName("Given invalid username, when updateUserCredential is called, then throw UserNotFoundException")
    void givenInvalidUsername_whenUpdateUserCredential_thenThrowException() {
        // given
        String username = "nonexistentuser";
        String password = "irrelevant";
        UpdateCredentialRequestTO requestTO = new UpdateCredentialRequestTO(username, password);

        Mockito.when(userIdentityRepository.findByUsernameWithAllRelations(username, true))
                .thenReturn(Optional.empty());

        // when / then
        Assertions.assertThrows(UserNotFoundException.class, () ->
                updateUserCredentialService.updateUserCredential(requestTO));

        Mockito.verify(userIdentityRepository).findByUsernameWithAllRelations(username, true);
        Mockito.verifyNoInteractions(identityUtils);
    }
}
