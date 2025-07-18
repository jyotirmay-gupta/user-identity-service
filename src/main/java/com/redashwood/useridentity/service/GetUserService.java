package com.redashwood.useridentity.service;

import com.redashwood.useridentity.dto.GetUserResponseTO;

public interface GetUserService {

    GetUserResponseTO getUserByEmailId(String emailId);

    GetUserResponseTO getUserByUsername(String username);
}
