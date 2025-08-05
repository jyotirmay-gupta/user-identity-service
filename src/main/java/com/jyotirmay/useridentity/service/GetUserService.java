package com.jyotirmay.useridentity.service;

import com.jyotirmay.useridentity.dto.GetUserResponseTO;

public interface GetUserService {

    GetUserResponseTO getUserByEmailId(String emailId);

    GetUserResponseTO getUserByUsername(String username);
}
