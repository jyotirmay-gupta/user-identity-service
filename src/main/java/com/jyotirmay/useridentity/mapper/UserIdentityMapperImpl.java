/*
 * Copyright (c) 2025 Jyotirmay Gupta
 *
 * Project: User Identity Service
 * Description: This is a personal project by Jyotirmay Gupta that implements a
 * user identity management service using Spring Boot. It provides functionality to
 * register and maintain user identities within a system.
 *
 * This code is intended for educational and personal use, demonstrating core backend
 * concepts such as RESTful API design, user registration, integration
 * with persistent storage using Spring Boot.
 *
 * Licensed under the Apache License Version 2.0. See LICENSE file for more details.
 */

package com.jyotirmay.useridentity.mapper;

import com.jyotirmay.useridentity.dto.AddressTO;
import com.jyotirmay.useridentity.dto.ContactTO;
import com.jyotirmay.useridentity.dto.RegisterUserRequestTO;
import com.jyotirmay.useridentity.dto.UpdateUserRequestTO;
import com.jyotirmay.useridentity.dto.UserInformationTO;
import com.jyotirmay.useridentity.entity.UserAddressEntity;
import com.jyotirmay.useridentity.entity.UserContactEntity;
import com.jyotirmay.useridentity.entity.UserCredentialEntity;
import com.jyotirmay.useridentity.entity.UserEntity;

public class UserIdentityMapperImpl implements UserIdentityMapper{

    @Override
    public UserEntity buildUserEntityFromRegisterUserRequestTO(RegisterUserRequestTO registerUserRequestTO) {

        UserContactEntity userContactEntity = buildUserContactEntity(registerUserRequestTO);
        UserAddressEntity userAddressEntity = buildUserAddressEntity(registerUserRequestTO);
        UserCredentialEntity userCredentialEntity = buildCredential(registerUserRequestTO);
        UserEntity userEntity = buildUserEntity(registerUserRequestTO, userAddressEntity, userContactEntity, userCredentialEntity);
        userEntity.getAddress().setUser(userEntity);
        userEntity.getContact().setUser(userEntity);
        userEntity.getCredential().setUser(userEntity);
        return userEntity;
    }

    @Override
    public void updateUserEntityFromUpdateUserRequestTO(UserEntity userEntity, UpdateUserRequestTO updateUserRequestTO) {

        updateUserContactEntity(updateUserRequestTO, userEntity);
        updateUserAddressEntity(updateUserRequestTO, userEntity);
        updateUserEntity(updateUserRequestTO, userEntity);
    }

    @Override
    public UserInformationTO buildUserInformationTOFromUserEntity(UserEntity userEntity) {
        ContactTO contactTO = buildContactTO(userEntity);
        AddressTO addressTO = buildAddressTO(userEntity);
        return buildUserInformationTO(userEntity, addressTO, contactTO);
    }

    private static UserContactEntity buildUserContactEntity(RegisterUserRequestTO registerUserRequestTO) {
        UserContactEntity userContactEntity = new UserContactEntity();
        userContactEntity.setEmail(registerUserRequestTO.contact().email());
        userContactEntity.setPrimaryPhoneNumber(registerUserRequestTO.contact().primaryPhoneNumber());
        userContactEntity.setSecondaryPhoneNumber(registerUserRequestTO.contact().secondaryPhoneNumber());
        return userContactEntity;
    }

    private static UserAddressEntity buildUserAddressEntity(RegisterUserRequestTO registerUserRequestTO) {
        UserAddressEntity userAddressEntity = new UserAddressEntity();
        userAddressEntity.setAddressLine1(registerUserRequestTO.address().addressLine1());
        userAddressEntity.setAddressLine2(registerUserRequestTO.address().addressLine2());
        userAddressEntity.setCity(registerUserRequestTO.address().city());
        userAddressEntity.setState(registerUserRequestTO.address().state());
        userAddressEntity.setPostalCode(registerUserRequestTO.address().postalCode());
        userAddressEntity.setCountry(registerUserRequestTO.address().country());
        return userAddressEntity;
    }

    private static UserCredentialEntity buildCredential(RegisterUserRequestTO registerUserRequestTO) {
        UserCredentialEntity userCredentialEntity = new UserCredentialEntity();
        userCredentialEntity.setUsername(registerUserRequestTO.username());
        return userCredentialEntity;
    }

    private static UserEntity buildUserEntity(RegisterUserRequestTO registerUserRequestTO, UserAddressEntity userAddressEntity,
                                              UserContactEntity userContactEntity, UserCredentialEntity userCredentialEntity) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(registerUserRequestTO.firstName());
        userEntity.setMiddleName(registerUserRequestTO.middleName());
        userEntity.setLastName(registerUserRequestTO.lastName());
        userEntity.setAge(registerUserRequestTO.age());
        userEntity.setGender(registerUserRequestTO.gender());
        userEntity.setAddress(userAddressEntity);
        userEntity.setContact(userContactEntity);
        userEntity.setCredential(userCredentialEntity);
        return userEntity;
    }

    private static ContactTO buildContactTO(UserEntity userEntity) {
        return new ContactTO(userEntity.getContact().getPrimaryPhoneNumber(),
                userEntity.getContact().getSecondaryPhoneNumber(),
                userEntity.getContact().getEmail());
    }

    private static UserInformationTO buildUserInformationTO(UserEntity userEntity, AddressTO addressTO, ContactTO contactTO) {
        return new UserInformationTO(userEntity.getFirstName(),
                userEntity.getMiddleName(),
                userEntity.getLastName(),
                userEntity.getAge(),
                userEntity.getGender(),
                addressTO,
                contactTO);
    }

    private static AddressTO buildAddressTO(UserEntity userEntity) {
        return new AddressTO(userEntity.getAddress().getAddressLine1(),
                userEntity.getAddress().getAddressLine2(),
                userEntity.getAddress().getCity(),
                userEntity.getAddress().getState(),
                userEntity.getAddress().getPostalCode(),
                userEntity.getAddress().getCountry());
    }

    private void updateUserContactEntity(UpdateUserRequestTO updateUserRequestTO, UserEntity userEntity) {

        userEntity.getContact().setPrimaryPhoneNumber(updateUserRequestTO.contact().primaryPhoneNumber());
        userEntity.getContact().setSecondaryPhoneNumber(updateUserRequestTO.contact().secondaryPhoneNumber());
        userEntity.getContact().setEmail(updateUserRequestTO.contact().email());

    }

    private void updateUserAddressEntity(UpdateUserRequestTO updateUserRequestTO, UserEntity userEntity) {

        userEntity.getAddress().setAddressLine1(updateUserRequestTO.address().addressLine1());
        userEntity.getAddress().setAddressLine2(updateUserRequestTO.address().addressLine2());
        userEntity.getAddress().setCity(updateUserRequestTO.address().city());
        userEntity.getAddress().setState(updateUserRequestTO.address().state());
        userEntity.getAddress().setPostalCode(updateUserRequestTO.address().postalCode());
        userEntity.getAddress().setCountry(updateUserRequestTO.address().country());

    }

    private void updateUserEntity(UpdateUserRequestTO updateUserRequestTO, UserEntity userEntity) {

        userEntity.setFirstName(updateUserRequestTO.firstName());
        userEntity.setMiddleName(updateUserRequestTO.middleName());
        userEntity.setLastName(updateUserRequestTO.lastName());
        userEntity.setAge(updateUserRequestTO.age());
        userEntity.setGender(updateUserRequestTO.gender());

    }

}
