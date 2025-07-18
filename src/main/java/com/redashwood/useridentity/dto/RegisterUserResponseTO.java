package com.redashwood.useridentity.dto;

public record RegisterUserResponseTO(

        UserInformationTO userInformation,

        CredentialTO credentials,

        ErrorTO error

) {

}
