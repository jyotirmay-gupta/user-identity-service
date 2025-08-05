package com.jyotirmay.useridentity.service.impl;

import com.jyotirmay.useridentity.dto.CredentialTO;
import com.jyotirmay.useridentity.dto.RegisterUserRequestTO;
import com.jyotirmay.useridentity.dto.RegisterUserResponseTO;
import com.jyotirmay.useridentity.dto.UserInformationTO;
import com.jyotirmay.useridentity.entity.UserEntity;
import com.jyotirmay.useridentity.exception.UserAlreadyExistsException;
import com.jyotirmay.useridentity.mapper.UserIdentityMapper;
import com.jyotirmay.useridentity.repository.UserIdentityRepository;
import com.jyotirmay.useridentity.service.RegisterUserService;
import com.jyotirmay.useridentity.util.IdentityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class RegisterUserServiceImpl implements RegisterUserService {

    private static final Logger LOGGER = LogManager.getLogger(RegisterUserServiceImpl.class);

    private final UserIdentityRepository userIdentityRepository;

    private final IdentityUtils identityUtils;

    private final UserIdentityMapper userIdentityMapper;

    public RegisterUserServiceImpl(UserIdentityRepository userIdentityRepository, IdentityUtils identityUtils, UserIdentityMapper userIdentityMapper) {
        this.userIdentityRepository = userIdentityRepository;
        this.identityUtils = identityUtils;
        this.userIdentityMapper = userIdentityMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, label = "register_user_tx")
    public RegisterUserResponseTO registerUser(RegisterUserRequestTO registerUserRequestTO) {

        userIdentityRepository.findByEmailWithAllRelations(registerUserRequestTO.contact().email(), true).ifPresent(u -> {
            throw new UserAlreadyExistsException("ERR400", "User already exists with emailId %s.", registerUserRequestTO.contact().email());
        });

        userIdentityRepository.findByUsernameWithAllRelations(registerUserRequestTO.username(), true).ifPresent(u -> {
            throw new UserAlreadyExistsException("ERR400", "User already exists with username %s.", registerUserRequestTO.username());
        });

        String password = identityUtils.generateRandomPassword();
        String encryptedPassword = identityUtils.encryptPassword(password);

        UserEntity userEntity = userIdentityMapper.buildUserEntityFromRegisterUserRequestTO(registerUserRequestTO);
        userEntity.getCredential().setPassword(encryptedPassword);

        userIdentityRepository.save(userEntity);

        LOGGER.info("User {} {} {} registered successfully with id: {}", userEntity.getFirstName(), userEntity.getMiddleName(), userEntity.getLastName(), userEntity.getUserId());

        CredentialTO credentialTO = buildCredentialTO(userEntity, password);

        UserInformationTO userInformationTO = userIdentityMapper.buildUserInformationTOFromUserEntity(userEntity);

        return new RegisterUserResponseTO(userInformationTO, credentialTO);
    }

    private static CredentialTO buildCredentialTO(UserEntity userEntity, String password) {
        return new CredentialTO(userEntity.getCredential().getUsername(), password);
    }


}
