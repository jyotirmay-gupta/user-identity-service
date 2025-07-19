package com.redashwood.useridentity.service.impl;

import com.redashwood.useridentity.dto.CredentialTO;
import com.redashwood.useridentity.dto.RegisterUserRequestTO;
import com.redashwood.useridentity.dto.RegisterUserResponseTO;
import com.redashwood.useridentity.dto.UserInformationTO;
import com.redashwood.useridentity.entity.UserEntity;
import com.redashwood.useridentity.mapper.UserIdentityMapper;
import com.redashwood.useridentity.repository.UserIdentityRepository;
import com.redashwood.useridentity.service.RegisterUserService;
import com.redashwood.useridentity.util.IdentityUtils;
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

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, label = "register_user_tx")
    @Override
    public RegisterUserResponseTO registerUser(RegisterUserRequestTO registerUserRequestTO) {

        String password = identityUtils.generateRandomPassword();
        String encryptedPassword = identityUtils.encryptPassword(password);

        UserEntity userEntity = userIdentityMapper.buildUserEntityFromRegisterUserRequestTO(registerUserRequestTO);
        userEntity.getCredential().setPassword(encryptedPassword);

        userIdentityRepository.save(userEntity);

        LOGGER.info("User {} {} {} registered successfully with id: {}", userEntity.getFirstName(),
                userEntity.getMiddleName(), userEntity.getLastName(), userEntity.getUserId());

        CredentialTO credentialTO = buildCredentialTO(userEntity, password);

        UserInformationTO userInformationTO = userIdentityMapper.buildUserInformationTOFromUserEntity(userEntity);

        return new RegisterUserResponseTO(userInformationTO, credentialTO, null);
    }

    private static CredentialTO buildCredentialTO(UserEntity userEntity, String password) {
        return new CredentialTO(userEntity.getCredential().getUsername(), password);
    }


}
