/*
 * Copyright (c) 2025 Jyotirmay Gupta
 *
 * Project: User Identity Service
 * Description: This is a personal project by Jyotirmay Gupta that implements a
 * user identity management service using Spring Boot. It provides functionality to
 * register and maintain user identities within a system.
 *
 * This code is intended for educational and personal use, demonstrating core backend
 * concepts such as RESTful API design, user registration, integration
 * with persistent storage using Spring Boot.
 *
 * Licensed under the Apache License Version 2.0. See LICENSE file for more details.
 */

package com.jyotirmay.useridentity.dto.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GenderEnum {

    MALE("Male"),
    FEMALE("Female"),
    UNSPECIFIED("Un-specified");

    private final String value;

    GenderEnum(String value) {
        this.value = value;
    }

    public static GenderEnum fromValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Gender value cannot be null or blank");
        }

        for (GenderEnum gender : GenderEnum.values()) {
            if (gender.getValue().equalsIgnoreCase(value.trim())) {
                return gender;
            }
        }

        throw new IllegalArgumentException("Unknown gender value: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
