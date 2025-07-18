package com.redashwood.useridentity.service;

import com.redashwood.useridentity.dto.UpdateUserRequestTO;
import com.redashwood.useridentity.dto.UpdateUserResponseTO;

public interface UpdateUserService {

    UpdateUserResponseTO updateUserByEmailId(UpdateUserRequestTO updateUserRequestTO, String emailId);

    UpdateUserResponseTO updateUserByUsername(UpdateUserRequestTO updateUserRequestTO, String username);
}
