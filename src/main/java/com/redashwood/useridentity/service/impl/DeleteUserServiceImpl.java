package com.redashwood.useridentity.service.impl;

import com.redashwood.useridentity.dto.DeleteUserResponseTO;
import com.redashwood.useridentity.repository.UserIdentityRepository;
import com.redashwood.useridentity.service.DeleteUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteUserServiceImpl implements DeleteUserService {

    private static final Logger LOGGER = LogManager.getLogger(DeleteUserServiceImpl.class);

    private final UserIdentityRepository userIdentityRepository;

    public DeleteUserServiceImpl(UserIdentityRepository userIdentityRepository) {
        this.userIdentityRepository = userIdentityRepository;
    }

    @Override
    public DeleteUserResponseTO deleteUserByEmailId(String emailId) {
        return null;
    }

    @Override
    public DeleteUserResponseTO deleteUserByUsername(String username) {
        return null;
    }
}
