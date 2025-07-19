package com.redashwood.useridentity.mapper;

import com.redashwood.useridentity.dto.RegisterUserRequestTO;
import com.redashwood.useridentity.dto.UpdateUserRequestTO;
import com.redashwood.useridentity.dto.UserInformationTO;
import com.redashwood.useridentity.entity.UserEntity;

public interface UserIdentityMapper {

    UserEntity buildUserEntityFromRegisterUserRequestTO(RegisterUserRequestTO registerUserRequestTO);

    void updateUserEntityFromUpdateUserRequestTO(UserEntity userEntity, UpdateUserRequestTO updateUserRequestTO);

    UserInformationTO buildUserInformationTOFromUserEntity(UserEntity userEntity);
}
