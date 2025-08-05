package com.jyotirmay.useridentity.service;

import com.jyotirmay.useridentity.dto.RegisterUserRequestTO;
import com.jyotirmay.useridentity.dto.RegisterUserResponseTO;

public interface RegisterUserService {

    RegisterUserResponseTO registerUser(RegisterUserRequestTO registerUserRequestTO);
}
