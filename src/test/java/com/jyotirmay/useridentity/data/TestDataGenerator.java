package com.jyotirmay.useridentity.data;

import com.jyotirmay.useridentity.dto.AddressTO;
import com.jyotirmay.useridentity.dto.ContactTO;
import com.jyotirmay.useridentity.dto.RegisterUserRequestTO;
import com.jyotirmay.useridentity.dto.UpdateUserRequestTO;
import com.jyotirmay.useridentity.dto.UserInformationTO;
import com.jyotirmay.useridentity.dto.enums.GenderEnum;
import com.jyotirmay.useridentity.entity.UserAddressEntity;
import com.jyotirmay.useridentity.entity.UserContactEntity;
import com.jyotirmay.useridentity.entity.UserCredentialEntity;
import com.jyotirmay.useridentity.entity.UserEntity;

import java.time.OffsetDateTime;
import java.util.UUID;

public class TestDataGenerator {

    private static UserEntity userEntity;

    private static final OffsetDateTime now = OffsetDateTime.now();

    private static final UUID uuid = UUID.randomUUID();

    public static UserEntity buildUserEntity() {

        if(null != userEntity)
            return userEntity;


        UserCredentialEntity userCredentialEntity = buildCredential(now);

        UserAddressEntity userAddressEntity = buildUserAddressEntity(now);

        UserContactEntity userContactEntity = buildUserContactEntity(now);

        userEntity = new UserEntity();
        userEntity.setUserId(uuid);
        userEntity.setFirstName("John");
        userEntity.setMiddleName("M.");
        userEntity.setLastName("Doe");
        userEntity.setAge(30);
        userEntity.setGender(GenderEnum.MALE);
        userEntity.setActive(true);
        userEntity.setContact(userContactEntity);
        userEntity.setAddress(userAddressEntity);
        userEntity.setCredential(userCredentialEntity);
        userEntity.setCreatedOn(now);

        userEntity.getCredential().setUser(userEntity);
        userEntity.getAddress().setUser(userEntity);
        userEntity.getContact().setUser(userEntity);
        return userEntity;
    }

    public static UserInformationTO buildUserInformationTO(){
        buildUserEntity();
        ContactTO contactTO = buildContactTO(userEntity);
        AddressTO addressTO = buildAddressTO(userEntity);
        return buildUserInformationTO(userEntity, addressTO, contactTO);
    }

    public static RegisterUserRequestTO buildRegisterUserRequestTO() {
        return new RegisterUserRequestTO(
                "John", "M.", "Doe", 30, GenderEnum.MALE,
                new AddressTO(
                        "White Sterling","3rd street", "Calgary","Calgary","562370","Canada"
                ),
                new ContactTO(
                        "011-574829","011-892345","john.doe@example.com"
                ),
                "johndoe123"
        );
    }

    public static UpdateUserRequestTO buildUpdateUserRequestTO(){
        return new UpdateUserRequestTO("John", "M.", "Doe", 30, GenderEnum.MALE,
                new AddressTO(
                        "White Sterling","3rd street", "Calgary","Calgary","562370","Canada"
                ),
                new ContactTO(
                        "011-574829","011-892345","john.doe@example.com"
                ));
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

    private static UserContactEntity buildUserContactEntity(OffsetDateTime now) {
        UserContactEntity userContactEntity = new UserContactEntity();
        userContactEntity.setContactId(1L);
        userContactEntity.setPrimaryPhoneNumber("011-574829");
        userContactEntity.setSecondaryPhoneNumber("011-892345");
        userContactEntity.setEmail("john.doe@example.com");
        userContactEntity.setActive(true);
        userContactEntity.setCreatedOn(now);
        return userContactEntity;
    }

    private static UserAddressEntity buildUserAddressEntity(OffsetDateTime now) {
        UserAddressEntity userAddressEntity = new UserAddressEntity();
        userAddressEntity.setAddressId(1L);
        userAddressEntity.setAddressLine1("White Sterling");
        userAddressEntity.setAddressLine2("3rd street");
        userAddressEntity.setCity("Calgary");
        userAddressEntity.setState("Calgary");
        userAddressEntity.setPostalCode("562370");
        userAddressEntity.setCountry("Canada");
        userAddressEntity.setCreatedOn(now);
        userAddressEntity.setActive(true);
        return userAddressEntity;
    }

    private static UserCredentialEntity buildCredential(OffsetDateTime now) {
        UserCredentialEntity userCredentialEntity = new UserCredentialEntity();
        userCredentialEntity.setUsername("johndoe123");
        userCredentialEntity.setPassword("$2a$10$6WpDxs8crBu44zH09j5cfuwTNaeAboAhNopIdNZdZlLA5O3yr8HKm");
        userCredentialEntity.setCredentialId(1L);
        userCredentialEntity.setCreatedOn(now);
        userCredentialEntity.setActive(true);
        return userCredentialEntity;
    }
}
