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
package com.jyotirmay.useridentity.util;

public class UserIdentityConstants {

    private UserIdentityConstants() {
    }

    public static final String BAD_REQUEST_ERROR_CODE = "ERR400";
    public static final String ACCEPT_VERSION = "Accept-Version";
    public static final String ACCEPT_VERSION_V1 = "v1";
}
