package com.redashwood.useridentity.service;

import com.redashwood.useridentity.dto.UpdateUserRequestTO;
import com.redashwood.useridentity.dto.UpdateUserResponseTO;

public interface UpdateUserService {

    UpdateUserResponseTO updateUser(UpdateUserRequestTO updateUserRequestTO);
}
