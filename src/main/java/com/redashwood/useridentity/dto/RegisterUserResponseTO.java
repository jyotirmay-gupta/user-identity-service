package com.redashwood.useridentity.dto;

public record RegisterUserResponseTO(

        String message,

        UserInformationTO userInformation,

        CredentialTO credentials,

        ErrorTO error

) {

}
