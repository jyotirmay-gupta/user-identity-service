package com.jyotirmay.useridentity.service;

import com.jyotirmay.useridentity.dto.DeleteUserResponseTO;

public interface DeleteUserService {

    DeleteUserResponseTO deleteUserByEmailId(String emailId);

    DeleteUserResponseTO deleteUserByUsername(String username);
}
