package com.jyotirmay.useridentity.mapper;

import com.jyotirmay.useridentity.dto.RegisterUserRequestTO;
import com.jyotirmay.useridentity.dto.UpdateUserRequestTO;
import com.jyotirmay.useridentity.dto.UserInformationTO;
import com.jyotirmay.useridentity.entity.UserEntity;

public interface UserIdentityMapper {

    UserEntity buildUserEntityFromRegisterUserRequestTO(RegisterUserRequestTO registerUserRequestTO);

    void updateUserEntityFromUpdateUserRequestTO(UserEntity userEntity, UpdateUserRequestTO updateUserRequestTO);

    UserInformationTO buildUserInformationTOFromUserEntity(UserEntity userEntity);
}
