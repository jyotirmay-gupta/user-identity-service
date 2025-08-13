package com.jyotirmay.useridentity.service.impl;

import com.jyotirmay.useridentity.dto.DeleteUserResponseTO;
import com.jyotirmay.useridentity.entity.UserEntity;
import com.jyotirmay.useridentity.exception.UserNotFoundException;
import com.jyotirmay.useridentity.repository.UserIdentityRepository;
import com.jyotirmay.useridentity.service.DeleteUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class DeleteUserServiceImpl implements DeleteUserService {

    private static final Logger LOGGER = LogManager.getLogger(DeleteUserServiceImpl.class);

    private final UserIdentityRepository userIdentityRepository;

    public DeleteUserServiceImpl(UserIdentityRepository userIdentityRepository) {
        this.userIdentityRepository = userIdentityRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, label = "delete_user_by_email_tx")
    public DeleteUserResponseTO deleteUserByEmailId(String emailId) {
        UserEntity userEntity = userIdentityRepository.findByEmailWithAllRelations(emailId, true)
                .orElseThrow(() -> new UserNotFoundException("ERR404", "User with emailId %s does not exist.", emailId));

        userEntity.setActive(false);
        userEntity.getAddress().setActive(false);
        userEntity.getContact().setActive(false);
        userEntity.getCredential().setActive(false);
        userIdentityRepository.save(userEntity);

        LOGGER.info("User {} {} {} deactivated successfully for id: {} and email: {}", userEntity.getFirstName(),
                userEntity.getMiddleName(), userEntity.getLastName(), userEntity.getUserId(), emailId);

        return new DeleteUserResponseTO(String.format("User with emailId %s deleted successfully", emailId));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, label = "delete_user_by_username_tx")
    public DeleteUserResponseTO deleteUserByUsername(String username) {
        UserEntity userEntity = userIdentityRepository.findByUsernameWithAllRelations(username, true)
                .orElseThrow(() -> new UserNotFoundException("ERR404", "User with username %s does not exist.", username));

        userEntity.setActive(false);
        userEntity.getAddress().setActive(false);
        userEntity.getContact().setActive(false);
        userEntity.getCredential().setActive(false);
        userIdentityRepository.save(userEntity);

        LOGGER.info("User {} {} {} deactivated successfully for id: {} and username: {}", userEntity.getFirstName(),
                userEntity.getMiddleName(), userEntity.getLastName(), userEntity.getUserId(), username);

        return new DeleteUserResponseTO(String.format("User with username %s deleted successfully", username));
    }
}
