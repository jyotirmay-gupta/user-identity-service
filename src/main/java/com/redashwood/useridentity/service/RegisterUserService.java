package com.redashwood.useridentity.service;

import com.redashwood.useridentity.dto.RegisterUserRequestTO;
import com.redashwood.useridentity.dto.RegisterUserResponseTO;

public interface RegisterUserService {

    RegisterUserResponseTO registerUser(RegisterUserRequestTO registerUserRequestTO);
}
