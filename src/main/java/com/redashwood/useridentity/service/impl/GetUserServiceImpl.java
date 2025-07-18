package com.redashwood.useridentity.service.impl;

import com.redashwood.useridentity.dto.GetUserResponseTO;
import com.redashwood.useridentity.repository.UserIdentityRepository;
import com.redashwood.useridentity.service.GetUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetUserServiceImpl implements GetUserService {

    private static final Logger LOGGER = LogManager.getLogger(GetUserServiceImpl.class);

    private final UserIdentityRepository userIdentityRepository;

    public GetUserServiceImpl(UserIdentityRepository userIdentityRepository) {
        this.userIdentityRepository = userIdentityRepository;
    }

    @Override
    public GetUserResponseTO getUserByEmailId(String emailId) {
        return null;
    }

    @Override
    public GetUserResponseTO getUserByUsername(String username) {
        return null;
    }
}
