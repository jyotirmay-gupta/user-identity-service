package com.jyotirmay.useridentity.service;

import com.jyotirmay.useridentity.data.TestDataGenerator;
import com.jyotirmay.useridentity.dto.GetUserResponseTO;
import com.jyotirmay.useridentity.dto.UserInformationTO;
import com.jyotirmay.useridentity.entity.UserEntity;
import com.jyotirmay.useridentity.exception.UserNotFoundException;
import com.jyotirmay.useridentity.mapper.UserIdentityMapper;
import com.jyotirmay.useridentity.repository.UserIdentityRepository;
import com.jyotirmay.useridentity.service.impl.GetUserServiceImpl;
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
public class GetUserServiceTest {

    @Mock
    private UserIdentityRepository userIdentityRepository;

    @Mock
    private UserIdentityMapper userIdentityMapper;

    @InjectMocks
    private GetUserServiceImpl getUserService;

    @Test
    @DisplayName("Given valid email, when getUserByEmail is called, then return user response")
    void givenValidEmail_whenGetUserByEmail_thenReturnsUserResponse() {
        // given
        String email = "john.doe@example.com";
        UserEntity userEntity = TestDataGenerator.buildUserEntity();
        UserInformationTO userInformationTO = TestDataGenerator.buildUserInformationTO();

        Mockito.when(userIdentityRepository.findByEmailWithAllRelations(email, true))
                .thenReturn(Optional.of(userEntity));

        Mockito.when(userIdentityMapper.buildUserInformationTOFromUserEntity(userEntity))
                .thenReturn(userInformationTO);

        // when
        GetUserResponseTO response = getUserService.getUserByEmail(email);

        // then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(userInformationTO, response.userInformation());

        Mockito.verify(userIdentityRepository).findByEmailWithAllRelations(email, true);
        Mockito.verify(userIdentityMapper).buildUserInformationTOFromUserEntity(userEntity);
    }

    @Test
    @DisplayName("Given non-existent email, when getUserByEmail is called, then throw UserNotFoundException")
    void givenInvalidEmail_whenGetUserByEmail_thenThrowException() {
        // given
        String email = "unknown@example.com";

        Mockito.when(userIdentityRepository.findByEmailWithAllRelations(email, true))
                .thenReturn(Optional.empty());

        // when / then
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            getUserService.getUserByEmail(email);
        });

        Mockito.verify(userIdentityRepository).findByEmailWithAllRelations(email, true);
        Mockito.verifyNoMoreInteractions(userIdentityMapper);
    }

    @Test
    @DisplayName("Given valid username, when getUserByUsername is called, then return user response")
    void givenValidUsername_whenGetUserByUsername_thenReturnsUserResponse() {
        // given
        String username = "johndoe123";
        UserEntity userEntity = TestDataGenerator.buildUserEntity();
        UserInformationTO userInformationTO = TestDataGenerator.buildUserInformationTO();

        Mockito.when(userIdentityRepository.findByUsernameWithAllRelations(username, true))
                .thenReturn(Optional.of(userEntity));

        Mockito.when(userIdentityMapper.buildUserInformationTOFromUserEntity(userEntity))
                .thenReturn(userInformationTO);

        // when
        GetUserResponseTO response = getUserService.getUserByUsername(username);

        // then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(userInformationTO, response.userInformation());

        Mockito.verify(userIdentityRepository).findByUsernameWithAllRelations(username, true);
        Mockito.verify(userIdentityMapper).buildUserInformationTOFromUserEntity(userEntity);
    }

    @Test
    @DisplayName("Given non-existent username, when getUserByUsername is called, then throw UserNotFoundException")
    void givenInvalidUsername_whenGetUserByUsername_thenThrowException() {
        // given
        String username = "invalid_user";

        Mockito.when(userIdentityRepository.findByUsernameWithAllRelations(username, true))
                .thenReturn(Optional.empty());

        // when / then
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            getUserService.getUserByUsername(username);
        });

        Mockito.verify(userIdentityRepository).findByUsernameWithAllRelations(username, true);
        Mockito.verifyNoMoreInteractions(userIdentityMapper);
    }
}
