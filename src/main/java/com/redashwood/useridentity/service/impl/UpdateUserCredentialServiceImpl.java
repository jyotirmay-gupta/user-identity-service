package com.redashwood.useridentity.service.impl;

import com.redashwood.useridentity.dto.UpdateCredentialRequestTO;
import com.redashwood.useridentity.dto.UpdateCredentialResponseTO;
import com.redashwood.useridentity.entity.UserEntity;
import com.redashwood.useridentity.exception.UserNotFoundException;
import com.redashwood.useridentity.repository.UserIdentityRepository;
import com.redashwood.useridentity.service.UpdateUserCredentialService;
import com.redashwood.useridentity.util.IdentityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateUserCredentialServiceImpl implements UpdateUserCredentialService {

    private static final Logger LOGGER = LogManager.getLogger(UpdateUserCredentialServiceImpl.class);

    private final UserIdentityRepository userIdentityRepository;

    private final IdentityUtils identityUtils;

    public UpdateUserCredentialServiceImpl(UserIdentityRepository userIdentityRepository, IdentityUtils identityUtils) {
        this.userIdentityRepository = userIdentityRepository;
        this.identityUtils = identityUtils;
    }

    @Override
    public UpdateCredentialResponseTO updateUserCredential(UpdateCredentialRequestTO updateCredentialRequestTO) {
        UserEntity userEntity = userIdentityRepository.findByUsernameWithAllRelations(updateCredentialRequestTO.username(), true)
                .orElseThrow(() -> new UserNotFoundException("ERR404", "User with username %s does not exist.", updateCredentialRequestTO.username()));

        String encryptedPassword = identityUtils.encryptPassword(updateCredentialRequestTO.password());
        userEntity.getCredential().setPassword(encryptedPassword);
        userIdentityRepository.save(userEntity);

        LOGGER.info("Credential for user {} {} {} updated successfully for id: {} and username: {}", userEntity.getFirstName(),
                userEntity.getMiddleName(), userEntity.getLastName(), userEntity.getUserId(), updateCredentialRequestTO.username());

        return new UpdateCredentialResponseTO(String.format("Credential for user with username %s updated successfully",
                updateCredentialRequestTO.username()));
    }
}
