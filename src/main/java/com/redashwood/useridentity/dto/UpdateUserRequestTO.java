package com.redashwood.useridentity.dto;

import com.redashwood.useridentity.dto.enums.GenderEnum;
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
