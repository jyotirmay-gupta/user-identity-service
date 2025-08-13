package com.jyotirmay.useridentity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateUserResponseTO(

        UserInformationTO userInformation
) {
}
