package com.jyotirmay.useridentity.exception;

public class UserAlreadyExistsException extends RuntimeException {

    private final String errorCode;

    public UserAlreadyExistsException(String errorCode, String errorMessage, Object... args) {
        super(String.format(errorMessage, args));
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
