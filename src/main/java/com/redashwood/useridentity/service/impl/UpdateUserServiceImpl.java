package com.redashwood.useridentity.service.impl;

import com.redashwood.useridentity.dto.UpdateUserRequestTO;
import com.redashwood.useridentity.dto.UpdateUserResponseTO;
import com.redashwood.useridentity.dto.UserInformationTO;
import com.redashwood.useridentity.entity.UserEntity;
import com.redashwood.useridentity.exception.UserNotFoundException;
import com.redashwood.useridentity.mapper.UserIdentityMapper;
import com.redashwood.useridentity.repository.UserIdentityRepository;
import com.redashwood.useridentity.service.UpdateUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class UpdateUserServiceImpl implements UpdateUserService {

    private static final Logger LOGGER = LogManager.getLogger(UpdateUserServiceImpl.class);

    private final UserIdentityRepository userIdentityRepository;

    private final UserIdentityMapper userIdentityMapper;

    public UpdateUserServiceImpl(UserIdentityRepository userIdentityRepository, UserIdentityMapper userIdentityMapper) {
        this.userIdentityRepository = userIdentityRepository;
        this.userIdentityMapper = userIdentityMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, label = "update_user_by_email_tx")
    public UpdateUserResponseTO updateUserByEmailId(UpdateUserRequestTO updateUserRequestTO, String emailId) {

        UserEntity userEntity = userIdentityRepository.findByEmailWithAllRelations(emailId)
                .orElseThrow(() -> new UserNotFoundException("ERR404", "User with emailId %s does not exist.", emailId));

        userIdentityMapper.updateUserEntityFromUpdateUserRequestTO(userEntity, updateUserRequestTO);

        userIdentityRepository.save(userEntity);

        LOGGER.info("User {} {} {} updated successfully for id: {} and email: {}", userEntity.getFirstName(),
                userEntity.getMiddleName(), userEntity.getLastName(), userEntity.getUserId(), emailId);

        UserInformationTO userInformationTO = userIdentityMapper.buildUserInformationTOFromUserEntity(userEntity);

        return new UpdateUserResponseTO(userInformationTO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, label = "update_user_by_username_tx")
    public UpdateUserResponseTO updateUserByUsername(UpdateUserRequestTO updateUserRequestTO, String username) {
        UserEntity userEntity = userIdentityRepository.findByUsernameWithAllRelations(username)
                .orElseThrow(() -> new UserNotFoundException("ERR404", "User with username %s does not exist.", username));

        userIdentityMapper.updateUserEntityFromUpdateUserRequestTO(userEntity, updateUserRequestTO);

        userIdentityRepository.save(userEntity);

        LOGGER.info("User {} {} {} updated successfully for id: {} and username: {}", userEntity.getFirstName(),
                userEntity.getMiddleName(), userEntity.getLastName(), userEntity.getUserId(), username);

        UserInformationTO userInformationTO = userIdentityMapper.buildUserInformationTOFromUserEntity(userEntity);

        return new UpdateUserResponseTO(userInformationTO);
    }
}
