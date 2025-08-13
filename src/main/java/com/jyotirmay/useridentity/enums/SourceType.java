package com.jyotirmay.useridentity.enums;

public enum SourceType {

    PARAM("param"),
    HEADER("header"),
    PATH("path");

    private final String value;

    SourceType(String value) {
        this.value = value;
    }

    /**
     * Returns the string representation of the source type,
     * typically used in logs or structured outputs.
     *
     * @return the lowercase string representation of the source type
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
