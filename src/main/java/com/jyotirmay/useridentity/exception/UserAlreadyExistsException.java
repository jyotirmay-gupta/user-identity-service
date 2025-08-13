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
