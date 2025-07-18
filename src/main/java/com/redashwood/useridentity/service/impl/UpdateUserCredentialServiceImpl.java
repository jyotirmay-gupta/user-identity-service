package com.redashwood.useridentity.service.impl;

import com.redashwood.useridentity.dto.UpdateCredentialRequestTO;
import com.redashwood.useridentity.dto.UpdateCredentialResponseTO;
import com.redashwood.useridentity.repository.UserIdentityRepository;
import com.redashwood.useridentity.service.UpdateUserCredentialService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateUserCredentialServiceImpl implements UpdateUserCredentialService {

    private static final Logger LOGGER = LogManager.getLogger(UpdateUserCredentialServiceImpl.class);

    private final UserIdentityRepository userIdentityRepository;

    public UpdateUserCredentialServiceImpl(UserIdentityRepository userIdentityRepository) {
        this.userIdentityRepository = userIdentityRepository;
    }

    @Override
    public UpdateCredentialResponseTO updateUserCredential(UpdateCredentialRequestTO updateCredentialRequestTO) {
        return null;
    }
}
