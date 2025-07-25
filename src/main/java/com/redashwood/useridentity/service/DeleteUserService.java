package com.redashwood.useridentity.service;

import com.redashwood.useridentity.dto.DeleteUserResponseTO;

public interface DeleteUserService {

    DeleteUserResponseTO deleteUserByEmailId(String emailId);

    DeleteUserResponseTO deleteUserByUsername(String username);
}
