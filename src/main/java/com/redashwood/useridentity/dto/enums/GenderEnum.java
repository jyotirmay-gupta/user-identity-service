package com.redashwood.useridentity.dto.enums;

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

    public String getValue() {
        return value;
    }
}
