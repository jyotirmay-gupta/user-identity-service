# User Identity Service API

**Copyright (c) 2025 Jyotirmay Gupta**

[![Build Status](https://github.com/jyotirmay-gupta/user-identity-service/actions/workflows/maven.yml/badge.svg)](https://github.com/jyotirmay-gupta/user-identity-service/actions/workflows/maven.yml)
[![License](https://img.shields.io/badge/License-Apache%202.0-brightgreen.svg)](LICENSE)
[![GitHub stars](https://img.shields.io/github/stars/jyotirmay-gupta/user-identity-service.svg)](https://github.com/jyotirmay-gupta/user-identity-service/stargazers)
[![GitHub last commit](https://img.shields.io/github/last-commit/jyotirmay-gupta/user-identity-service.svg)](https://github.com/jyotirmay-gupta/user-identity-service/commits)
[![Contributors](https://img.shields.io/github/contributors/jyotirmay-gupta/user-identity-service.svg)](https://github.com/jyotirmay-gupta/user-identity-service/graphs/contributors)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-v3.5.3-brightgreen)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-brightgreen)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14.5-brightgreen)](https://www.postgresql.org/)
[![codecov](https://codecov.io/github/jyotirmay-gupta/user-identity-service/graph/badge.svg?token=01HKXVTXK0)](https://codecov.io/github/jyotirmay-gupta/user-identity-service)

**Project:** User Identity Service  
**Description:** This service manages user identity data including registration, retrieval, update, deletion, and credential management. It provides a RESTful API for user lifecycle operations, supporting versioning through the `Accept-Version` header.

Licensed under the Apache License Version 2.0. See LICENSE file for details.

---

## Overview

This Spring Boot project offers REST endpoints to register users, update user info by email or username, fetch user details, delete users, and update credentials. The API is designed for reuse across projects as a centralized identity management system.

All endpoints require the header:  
`Accept-Version: v1`

---

## API Endpoints

### Register User

- **Endpoint:** `POST /user/identity`
- **Consumes:** `application/json`
- **Produces:** `application/json`
- **Request Body:** `RegisterUserRequestTO`
- **Response:** `RegisterUserResponseTO`
- **Description:** Registers a new user with personal details, address, and contact info. A default password is generated and stored encrypted.

---

### Update User by Email

- **Endpoint:** `PUT /user/identity?email={email}`
- **Consumes:** `application/json`
- **Produces:** `application/json`
- **Request Body:** `UpdateUserRequestTO`
- **Query Parameter:** `email` (validated as a non-blank, valid email)
- **Response:** `UpdateUserResponseTO`
- **Description:** Updates user details identified by email.

---

### Update User by Username

- **Endpoint:** `PUT /user/identity?username={username}`
- **Consumes:** `application/json`
- **Produces:** `application/json`
- **Request Body:** `UpdateUserRequestTO`
- **Query Parameter:** `username` (non-blank)
- **Response:** `UpdateUserResponseTO`
- **Description:** Updates user details identified by username.

---

### Get User by Email

- **Endpoint:** `GET /user/identity?email={email}`
- **Produces:** `application/json`
- **Query Parameter:** `email` (validated as a non-blank, valid email)
- **Response:** `GetUserResponseTO`
- **Description:** Retrieves user information by email.

---

### Get User by Username

- **Endpoint:** `GET /user/identity?username={username}`
- **Produces:** `application/json`
- **Query Parameter:** `username` (non-blank)
- **Response:** `GetUserResponseTO`
- **Description:** Retrieves user information by username.

---

### Delete User by Email

- **Endpoint:** `DELETE /user/identity?email={email}`
- **Produces:** `application/json`
- **Query Parameter:** `email` (validated as a non-blank, valid email)
- **Response:** `DeleteUserResponseTO`
- **Description:** Deletes a user identified by email.

---

### Delete User by Username

- **Endpoint:** `DELETE /user/identity?username={username}`
- **Produces:** `application/json`
- **Query Parameter:** `username` (non-blank)
- **Response:** `DeleteUserResponseTO`
- **Description:** Deletes a user identified by username.

---

### Update User Credentials

- **Endpoint:** `PUT /user/credential`
- **Consumes:** `application/json`
- **Produces:** `application/json`
- **Request Body:** `UpdateCredentialRequestTO`
- **Response:** `UpdateCredentialResponseTO`
- **Description:** Updates username and/or password credentials for a user.

---

## Technologies Used

- Java 21
- Spring Boot
- Spring Web (REST API)
- Hibernate / JPA
- PostgreSQL

---

## How to Run

1. Clone the repository
2. Configure database in `application.yml`
3. Build project with Maven
4. Run Spring Boot application
5. Use Postman or curl to test the API

---

## Notes

- Validation is applied on request parameters and bodies using Jakarta Bean Validation.
- API versioning via `Accept-Version` header ensures backward compatibility.
- Logging added for critical user operations.

---

## License

Licensed under the Apache License Version 2.0. See LICENSE file for details.

---

*Created by Jyotirmay Gupta — 2025*
