package com.jyotirmay.useridentity.service;

import com.jyotirmay.useridentity.data.TestDataGenerator;
import com.jyotirmay.useridentity.dto.UpdateUserRequestTO;
import com.jyotirmay.useridentity.dto.UpdateUserResponseTO;
import com.jyotirmay.useridentity.dto.UserInformationTO;
import com.jyotirmay.useridentity.entity.UserEntity;
import com.jyotirmay.useridentity.exception.UserNotFoundException;
import com.jyotirmay.useridentity.mapper.UserIdentityMapper;
import com.jyotirmay.useridentity.repository.UserIdentityRepository;
import com.jyotirmay.useridentity.service.impl.UpdateUserServiceImpl;
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
public class UpdateUserServiceTest {

    @Mock
    private UserIdentityRepository userIdentityRepository;

    @Mock
    private UserIdentityMapper userIdentityMapper;

    @InjectMocks
    private UpdateUserServiceImpl updateUserService;

    @Test
    @DisplayName("Given valid email, when updateUserByEmailId is called, then user is updated successfully")
    void givenValidEmail_whenUpdateUserByEmailId_thenUpdatesUserSuccessfully() {
        // given
        String email = "john.doe@example.com";
        UpdateUserRequestTO requestTO = TestDataGenerator.buildUpdateUserRequestTO();
        UserEntity existingUser = TestDataGenerator.buildUserEntity();  // with active = true

        Mockito.when(userIdentityRepository.findByEmailWithAllRelations(email, true))
                .thenReturn(Optional.of(existingUser));

        UserInformationTO updatedInfo = TestDataGenerator.buildUserInformationTO();
        Mockito.when(userIdentityMapper.buildUserInformationTOFromUserEntity(existingUser))
                .thenReturn(updatedInfo);

        // when
        UpdateUserResponseTO responseTO = updateUserService.updateUserByEmailId(requestTO, email);

        // then
        Assertions.assertNotNull(responseTO);
        Assertions.assertEquals(updatedInfo, responseTO.userInformation());

        Mockito.verify(userIdentityRepository).findByEmailWithAllRelations(email, true);
        Mockito.verify(userIdentityMapper).updateUserEntityFromUpdateUserRequestTO(existingUser, requestTO);
        Mockito.verify(userIdentityRepository).save(existingUser);
        Mockito.verify(userIdentityMapper).buildUserInformationTOFromUserEntity(existingUser);
    }

    @Test
    @DisplayName("Given non-existent email, when updateUserByEmailId is called, then throws UserNotFoundException")
    void givenInvalidEmail_whenUpdateUserByEmailId_thenThrowsException() {
        // given
        String email = "nonexistent@example.com";
        UpdateUserRequestTO requestTO = TestDataGenerator.buildUpdateUserRequestTO();

        Mockito.when(userIdentityRepository.findByEmailWithAllRelations(email, true))
                .thenReturn(Optional.empty());

        // when / then
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            updateUserService.updateUserByEmailId(requestTO, email);
        });

        Mockito.verify(userIdentityRepository).findByEmailWithAllRelations(email, true);
        Mockito.verifyNoMoreInteractions(userIdentityMapper, userIdentityRepository);
    }

    @Test
    @DisplayName("Given valid username, when updateUserByUsername is called, then user is updated successfully")
    void givenValidUsername_whenUpdateUserByUsername_thenUpdatesUserSuccessfully() {
        // given
        String username = "johndoe123";
        UpdateUserRequestTO requestTO = TestDataGenerator.buildUpdateUserRequestTO();
        UserEntity existingUser = TestDataGenerator.buildUserEntity();

        Mockito.when(userIdentityRepository.findByUsernameWithAllRelations(username, true))
                .thenReturn(Optional.of(existingUser));

        UserInformationTO updatedInfo = TestDataGenerator.buildUserInformationTO();
        Mockito.when(userIdentityMapper.buildUserInformationTOFromUserEntity(existingUser))
                .thenReturn(updatedInfo);

        // when
        UpdateUserResponseTO responseTO = updateUserService.updateUserByUsername(requestTO, username);

        // then
        Assertions.assertNotNull(responseTO);
        Assertions.assertEquals(updatedInfo, responseTO.userInformation());

        Mockito.verify(userIdentityRepository).findByUsernameWithAllRelations(username, true);
        Mockito.verify(userIdentityMapper).updateUserEntityFromUpdateUserRequestTO(existingUser, requestTO);
        Mockito.verify(userIdentityRepository).save(existingUser);
        Mockito.verify(userIdentityMapper).buildUserInformationTOFromUserEntity(existingUser);
    }

    @Test
    @DisplayName("Given non-existent username, when updateUserByUsername is called, then throws UserNotFoundException")
    void givenInvalidUsername_whenUpdateUserByUsername_thenThrowsException() {
        // given
        String username = "unknownuser";
        UpdateUserRequestTO requestTO = TestDataGenerator.buildUpdateUserRequestTO();

        Mockito.when(userIdentityRepository.findByUsernameWithAllRelations(username, true))
                .thenReturn(Optional.empty());

        // when / then
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            updateUserService.updateUserByUsername(requestTO, username);
        });

        Mockito.verify(userIdentityRepository).findByUsernameWithAllRelations(username, true);
        Mockito.verifyNoMoreInteractions(userIdentityMapper, userIdentityRepository);
    }
}
