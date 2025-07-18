package com.redashwood.useridentity.service;

import com.redashwood.useridentity.dto.GetUserByEmailRequestTO;
import com.redashwood.useridentity.dto.GetUserByUsernameRequestTO;
import com.redashwood.useridentity.dto.GetUserResponseTO;

public interface GetUserService {

    GetUserResponseTO getUserByEmailId(GetUserByEmailRequestTO getUserByEmailRequestTO);

    GetUserResponseTO getUserByUsername(GetUserByUsernameRequestTO getUserByUsernameRequestTO);
}
