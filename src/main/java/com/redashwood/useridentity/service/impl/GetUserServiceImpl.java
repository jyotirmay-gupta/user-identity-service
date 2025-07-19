package com.redashwood.useridentity.service.impl;

import com.redashwood.useridentity.dto.GetUserResponseTO;
import com.redashwood.useridentity.dto.UserInformationTO;
import com.redashwood.useridentity.entity.UserEntity;
import com.redashwood.useridentity.exception.UserNotFoundException;
import com.redashwood.useridentity.mapper.UserIdentityMapper;
import com.redashwood.useridentity.repository.UserIdentityRepository;
import com.redashwood.useridentity.service.GetUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetUserServiceImpl implements GetUserService {

    private static final Logger LOGGER = LogManager.getLogger(GetUserServiceImpl.class);

    private final UserIdentityRepository userIdentityRepository;

    private final UserIdentityMapper userIdentityMapper;

    public GetUserServiceImpl(UserIdentityRepository userIdentityRepository, UserIdentityMapper userIdentityMapper) {
        this.userIdentityRepository = userIdentityRepository;
        this.userIdentityMapper = userIdentityMapper;
    }

    @Override
    public GetUserResponseTO getUserByEmailId(String emailId) {
        UserEntity userEntity = userIdentityRepository.findByEmailWithAllRelations(emailId)
                .orElseThrow(() -> new UserNotFoundException("ERR404", "User with emailId %s not found.", emailId));

        LOGGER.info("User {} {} {} fetched successfully for id: {} and email: {}", userEntity.getFirstName(),
                userEntity.getMiddleName(), userEntity.getLastName(), userEntity.getUserId(), emailId);

        UserInformationTO userInformationTO = userIdentityMapper.buildUserInformationTOFromUserEntity(userEntity);

        return new GetUserResponseTO(userInformationTO);
    }

    @Override
    public GetUserResponseTO getUserByUsername(String username) {
        UserEntity userEntity = userIdentityRepository.findByUsernameWithAllRelations(username)
                .orElseThrow(() -> new UserNotFoundException("ERR404", "User with username %s not found.", username));

        LOGGER.info("User {} {} {} fetched successfully for id: {} and username: {}", userEntity.getFirstName(),
                userEntity.getMiddleName(), userEntity.getLastName(), userEntity.getUserId(), username);

        UserInformationTO userInformationTO = userIdentityMapper.buildUserInformationTOFromUserEntity(userEntity);

        return new GetUserResponseTO(userInformationTO);
    }
}
