package com.redashwood.useridentity.service.impl;

import com.redashwood.useridentity.dto.RegisterUserRequestTO;
import com.redashwood.useridentity.dto.RegisterUserResponseTO;
import com.redashwood.useridentity.repository.UserIdentityRepository;
import com.redashwood.useridentity.service.RegisterUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegisterUserServiceImpl implements RegisterUserService {

    private static final Logger LOGGER = LogManager.getLogger(RegisterUserServiceImpl.class);

    private final UserIdentityRepository userIdentityRepository;

    public RegisterUserServiceImpl(UserIdentityRepository userIdentityRepository) {
        this.userIdentityRepository = userIdentityRepository;
    }

    @Override
    public RegisterUserResponseTO registerUser(RegisterUserRequestTO registerUserRequestTO) {
        return null;
    }
}
