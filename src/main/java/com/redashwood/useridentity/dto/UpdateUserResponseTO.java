package com.redashwood.useridentity.dto;

public record UpdateUserResponseTO(

        UserInformationTO userInformation,

        ErrorTO error
) {
}
