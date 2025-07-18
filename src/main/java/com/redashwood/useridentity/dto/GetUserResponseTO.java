package com.redashwood.useridentity.dto;

public record GetUserResponseTO(

        UserInformationTO userInformation,

        ErrorTO error
) {
}
