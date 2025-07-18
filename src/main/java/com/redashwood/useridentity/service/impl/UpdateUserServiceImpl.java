package com.redashwood.useridentity.service.impl;

import com.redashwood.useridentity.dto.UpdateUserRequestTO;
import com.redashwood.useridentity.dto.UpdateUserResponseTO;
import com.redashwood.useridentity.repository.UserIdentityRepository;
import com.redashwood.useridentity.service.UpdateUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateUserServiceImpl implements UpdateUserService {

    private static final Logger LOGGER = LogManager.getLogger(UpdateUserServiceImpl.class);

    private final UserIdentityRepository userIdentityRepository;

    public UpdateUserServiceImpl(UserIdentityRepository userIdentityRepository) {
        this.userIdentityRepository = userIdentityRepository;
    }

    @Override
    public UpdateUserResponseTO updateUserByEmailId(UpdateUserRequestTO updateUserRequestTO, String emailId) {
        return null;
    }

    @Override
    public UpdateUserResponseTO updateUserByUsername(UpdateUserRequestTO updateUserRequestTO, String username) {
        return null;
    }
}
