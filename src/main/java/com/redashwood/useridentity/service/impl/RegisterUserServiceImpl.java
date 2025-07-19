package com.redashwood.useridentity.service.impl;

import com.redashwood.useridentity.dto.*;
import com.redashwood.useridentity.entity.UserAddressEntity;
import com.redashwood.useridentity.entity.UserContactEntity;
import com.redashwood.useridentity.entity.UserCredentialEntity;
import com.redashwood.useridentity.entity.UserEntity;
import com.redashwood.useridentity.repository.UserIdentityRepository;
import com.redashwood.useridentity.service.RegisterUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class RegisterUserServiceImpl implements RegisterUserService {

    private static final Logger LOGGER = LogManager.getLogger(RegisterUserServiceImpl.class);

    private final UserIdentityRepository userIdentityRepository;

    public RegisterUserServiceImpl(UserIdentityRepository userIdentityRepository) {
        this.userIdentityRepository = userIdentityRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, label = "register_user_tx")
    @Override
    public RegisterUserResponseTO registerUser(RegisterUserRequestTO registerUserRequestTO) {

        UserContactEntity userContactEntity = buildUserContactEntity(registerUserRequestTO);

        UserCredentialEntity userCredentialEntity = buildCredential(registerUserRequestTO);

        UserAddressEntity userAddressEntity = buildUserAddressEntity(registerUserRequestTO);

        UserEntity userEntity = buildUserEntity(registerUserRequestTO, userAddressEntity, userContactEntity, userCredentialEntity);

        userEntity.getAddress().setUser(userEntity);
        userEntity.getContact().setUser(userEntity);
        userEntity.getCredential().setUser(userEntity);

        userIdentityRepository.save(userEntity);

        LOGGER.info("User {} {} {} registered successfully with id: {}", userEntity.getFirstName(),
                userEntity.getMiddleName(), userEntity.getLastName(), userEntity.getUserId());

        ContactTO contactTO = buildContactTO(userEntity);

        AddressTO addressTO = buildAddressTO(userEntity);

        CredentialTO credentialTO = buildCredentialTO(userEntity);

        UserInformationTO userInformationTO = buildUserInformationTO(userEntity, addressTO, contactTO);

        return new RegisterUserResponseTO(userInformationTO,
                credentialTO,
                null);
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

    private static CredentialTO buildCredentialTO(UserEntity userEntity) {
        return new CredentialTO(userEntity.getCredential().getUsername(),
                userEntity.getCredential().getPassword());
    }

    private static AddressTO buildAddressTO(UserEntity userEntity) {
        return new AddressTO(userEntity.getAddress().getAddressLine1(),
                userEntity.getAddress().getAddressLine2(),
                userEntity.getAddress().getCity(),
                userEntity.getAddress().getState(),
                userEntity.getAddress().getPostalCode(),
                userEntity.getAddress().getCountry());
    }

    private static ContactTO buildContactTO(UserEntity userEntity) {
        return new ContactTO(userEntity.getContact().getPrimaryPhoneNumber(),
                userEntity.getContact().getSecondaryPhoneNumber(),
                userEntity.getContact().getEmail());
    }

    private static UserEntity buildUserEntity(RegisterUserRequestTO registerUserRequestTO, UserAddressEntity userAddressEntity, UserContactEntity userContactEntity, UserCredentialEntity userCredentialEntity) {
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

    private static UserContactEntity buildUserContactEntity(RegisterUserRequestTO registerUserRequestTO) {
        UserContactEntity userContactEntity = new UserContactEntity();
        userContactEntity.setEmail(registerUserRequestTO.contact().email());
        userContactEntity.setPrimaryPhoneNumber(registerUserRequestTO.contact().primaryPhoneNumber());
        userContactEntity.setSecondaryPhoneNumber(registerUserRequestTO.contact().secondaryPhoneNumber());
        return userContactEntity;
    }

    private static UserCredentialEntity buildCredential(RegisterUserRequestTO registerUserRequestTO) {
        UserCredentialEntity userCredentialEntity = new UserCredentialEntity();
        userCredentialEntity.setUsername(registerUserRequestTO.username());
        String password = null;
        userCredentialEntity.setPassword(password);
        return userCredentialEntity;
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
}
