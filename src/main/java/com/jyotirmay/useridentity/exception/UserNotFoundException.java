package com.jyotirmay.useridentity.exception;

public class UserNotFoundException extends RuntimeException {

    private final String errorCode;

    public UserNotFoundException(String errorCode, String errorMessage, Object... args) {
        super(String.format(errorMessage, args));
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
