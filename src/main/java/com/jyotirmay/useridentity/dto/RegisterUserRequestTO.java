package com.jyotirmay.useridentity.dto;

import com.jyotirmay.useridentity.dto.enums.GenderEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record RegisterUserRequestTO(

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
        ContactTO contact,

        @NotBlank(message = "Username is required")
        @Size(max = 50, message = "Username must be at most 50 characters")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username must be alphanumeric and may include underscores only (no symbols or spaces)")
        String username

) {
}
