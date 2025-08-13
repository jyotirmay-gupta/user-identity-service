package com.jyotirmay.useridentity.enums;

public enum IdentifierType {

    EMAIL("email"),
    USERNAME("username");

    private final String value;

    IdentifierType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value; // Optional: use in log statements
    }
}
