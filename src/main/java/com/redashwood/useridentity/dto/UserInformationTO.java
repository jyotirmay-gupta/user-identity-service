package com.redashwood.useridentity.dto;

import com.redashwood.useridentity.dto.enums.GenderEnum;

public record UserInformationTO(

        String firstName,

        String middleName,

        String lastName,

        Integer age,

        GenderEnum gender,

        AddressTO address,

        ContactTO contact) {
}
