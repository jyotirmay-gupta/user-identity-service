package com.redashwood.useridentity.dto;

import com.redashwood.useridentity.dto.enums.GenderEnum;

import java.util.UUID;

public record RegisterUserRequestTO(

        String firstName,

        String middleName,

        String lastName,

        Integer age,

        GenderEnum gender,

        AddressTO address,

        ContactTO contact

) {
}
