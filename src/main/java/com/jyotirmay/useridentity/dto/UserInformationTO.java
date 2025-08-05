package com.jyotirmay.useridentity.dto;

import com.jyotirmay.useridentity.dto.enums.GenderEnum;

public record UserInformationTO(

        String firstName,

        String middleName,

        String lastName,

        Integer age,

        GenderEnum gender,

        AddressTO address,

        ContactTO contact) {
}
