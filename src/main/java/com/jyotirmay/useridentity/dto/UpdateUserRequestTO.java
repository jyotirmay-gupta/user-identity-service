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

package com.jyotirmay.useridentity.dto;

import com.jyotirmay.useridentity.dto.enums.GenderEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record UpdateUserRequestTO(

        @NotBlank(message = "First name is required")
        @Size(max = 50, message = "First name must be at most 50 characters")
        String firstName,

        @Size(max = 50, message = "Middle name must be at most 50 characters")
        String middleName,

        @Size(max = 50, message = "Last name must be at most 50 characters")
        String lastName,

        @NotNull(message = "Age is required")
        @Min(value = 0, message = "Age must be non-negative")
        @Max(value = 150, message = "Age must be less than or equal to 150")
        Integer age,

        @NotNull(message = "Gender is required")
        GenderEnum gender,

        @Valid @NotNull(message = "Address is required")
        AddressTO address,

        @Valid @NotNull(message = "Contact is required")
        ContactTO contact
) {


}
