package com.jyotirmay.useridentity.service;

import com.jyotirmay.useridentity.dto.UpdateUserRequestTO;
import com.jyotirmay.useridentity.dto.UpdateUserResponseTO;

public interface UpdateUserService {

    UpdateUserResponseTO updateUserByEmailId(UpdateUserRequestTO updateUserRequestTO, String emailId);

    UpdateUserResponseTO updateUserByUsername(UpdateUserRequestTO updateUserRequestTO, String username);
}
