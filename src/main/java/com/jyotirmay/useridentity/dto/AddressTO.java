package com.jyotirmay.useridentity.dto;

public record AddressTO(

        String addressLine1,

        String addressLine2,

        String city,

        String state,

        String postalCode,

        String country
) {
}
