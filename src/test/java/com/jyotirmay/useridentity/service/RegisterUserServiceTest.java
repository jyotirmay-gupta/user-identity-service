package com.jyotirmay.useridentity.service;

import com.jyotirmay.useridentity.data.TestDataGenerator;
import com.jyotirmay.useridentity.dto.RegisterUserRequestTO;
import com.jyotirmay.useridentity.dto.RegisterUserResponseTO;
import com.jyotirmay.useridentity.dto.UserInformationTO;
import com.jyotirmay.useridentity.entity.UserEntity;
import com.jyotirmay.useridentity.exception.UserAlreadyExistsException;
import com.jyotirmay.useridentity.mapper.UserIdentityMapper;
import com.jyotirmay.useridentity.repository.UserIdentityRepository;
import com.jyotirmay.useridentity.service.impl.RegisterUserServiceImpl;
import com.jyotirmay.useridentity.util.IdentityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RegisterUserServiceTest {

    @Mock
    private UserIdentityRepository userIdentityRepository;

    @Mock
    private IdentityUtils identityUtils;

    @Mock
    private UserIdentityMapper userIdentityMapper;

    @InjectMocks
    private RegisterUserServiceImpl registerUserService;

    @Test
    void givenNewUser_whenRegisterUserCalled_thenRegistersSuccessfully() {
        RegisterUserRequestTO requestTO = TestDataGenerator.buildRegisterUserRequestTO();
        String rawPassword = "randomPassword123";
        String encryptedPassword = "encryptedPassword123";

        // Simulate no existing user
        Mockito.when(userIdentityRepository.findByEmailOrUsernameWithAllRelations(
                requestTO.contact().email(), requestTO.username(), true)).thenReturn(Optional.empty());

        Mockito.when(identityUtils.generateRandomPassword()).thenReturn(rawPassword);
        Mockito.when(identityUtils.encryptPassword(rawPassword)).thenReturn(encryptedPassword);

        UserEntity mappedUserEntity = TestDataGenerator.buildUserEntity();
        Mockito.when(userIdentityMapper.buildUserEntityFromRegisterUserRequestTO(requestTO)).thenReturn(mappedUserEntity);

        // Simulate save returns the same user entity with ID set
        Mockito.when(userIdentityRepository.save(mappedUserEntity)).thenReturn(mappedUserEntity);

        UserInformationTO userInformationTO = TestDataGenerator.buildUserInformationTO();
        Mockito.when(userIdentityMapper.buildUserInformationTOFromUserEntity(mappedUserEntity)).thenReturn(userInformationTO);

        RegisterUserResponseTO responseTO = registerUserService.registerUser(requestTO);

        Assertions.assertNotNull(responseTO);
        Assertions.assertEquals(userInformationTO, responseTO.userInformation());
        Assertions.assertEquals(mappedUserEntity.getCredential().getUsername(), responseTO.credentials().username());
        Assertions.assertEquals(rawPassword, responseTO.credentials().password()); // Raw password is returned, not encrypted

        Mockito.verify(userIdentityRepository).findByEmailOrUsernameWithAllRelations(requestTO.contact().email(), requestTO.username(), true);
        Mockito.verify(identityUtils).generateRandomPassword();
        Mockito.verify(identityUtils).encryptPassword(rawPassword);
        Mockito.verify(userIdentityMapper).buildUserEntityFromRegisterUserRequestTO(requestTO);
        Mockito.verify(userIdentityRepository).save(mappedUserEntity);
        Mockito.verify(userIdentityMapper).buildUserInformationTOFromUserEntity(mappedUserEntity);
    }

    @Test
    void givenExistingUser_whenRegisterUserCalled_thenThrowsUserAlreadyExistsException() {
        RegisterUserRequestTO requestTO = TestDataGenerator.buildRegisterUserRequestTO();

        UserEntity existingUser = new UserEntity();
        Mockito.when(userIdentityRepository.findByEmailOrUsernameWithAllRelations(
                        requestTO.contact().email(), requestTO.username(), true))
                .thenReturn(Optional.of(existingUser));

        UserAlreadyExistsException exception = Assertions.assertThrows(UserAlreadyExistsException.class,
                () -> registerUserService.registerUser(requestTO));

        Assertions.assertTrue(exception.getMessage().contains("User already exists"));
        Mockito.verify(userIdentityRepository).findByEmailOrUsernameWithAllRelations(requestTO.contact().email(), requestTO.username(), true);
        Mockito.verifyNoMoreInteractions(identityUtils, userIdentityMapper, userIdentityRepository);
    }
}
