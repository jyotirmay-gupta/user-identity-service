-- 1. Create the database
CREATE DATABASE user_identity_db;

-- Connect to the database
\c user_identity_db;

-- 2. Create the schema
CREATE SCHEMA user_identity_schema;

-- 3. Create a user with password
CREATE USER identity_user WITH PASSWORD 'password';

-- 4. Grant privileges to the user on the schema
GRANT USAGE ON SCHEMA user_identity_schema TO identity_user;
GRANT CREATE ON SCHEMA user_identity_schema TO identity_user;

-- Optional: Allow full access on all objects within the schema
ALTER DEFAULT PRIVILEGES IN SCHEMA user_identity_schema
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO identity_user;
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA user_identity_schema TO identity_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA user_identity_schema TO identity_user;

ALTER SCHEMA user_identity_schema OWNER TO identity_user;

-- Schema: user_identity_schema

-- UserEntity
CREATE TABLE user_identity_schema.user_identity
(
    user_id     UUID PRIMARY KEY,
    fist_name   VARCHAR(255)             NOT NULL,
    middle_name VARCHAR(255),
    last_name   VARCHAR(255),
    age         INT,
    gender      VARCHAR(50),
    active      BOOLEAN                  NOT NULL,
    created_on  TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_on  TIMESTAMP WITH TIME ZONE
);

-- UserCredentialEntity
CREATE TABLE user_identity_schema.user_credential
(
    credential_id BIGSERIAL PRIMARY KEY,
    username      VARCHAR(255),
    password      VARCHAR(255),
    user_id       UUID UNIQUE              NOT NULL,
    active        BOOLEAN                  NOT NULL,
    created_on    TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_on    TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_user_credential FOREIGN KEY (user_id) REFERENCES user_identity_schema.user_identity (user_id) ON DELETE CASCADE
);

-- UserAddressEntity
CREATE TABLE user_identity_schema.user_address
(
    address_id    BIGSERIAL PRIMARY KEY,
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    city          VARCHAR(100),
    state         VARCHAR(100),
    postal_code   VARCHAR(20),
    country       VARCHAR(100),
    user_id       UUID UNIQUE              NOT NULL,
    active        BOOLEAN                  NOT NULL,
    created_on    TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_on    TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_user_address FOREIGN KEY (user_id) REFERENCES user_identity_schema.user_identity (user_id) ON DELETE CASCADE
);

-- UserContactEntity
CREATE TABLE user_identity_schema.user_contact
(
    contact_id             BIGSERIAL PRIMARY KEY,
    primary_phone_number   VARCHAR(20),
    secondary_phone_number VARCHAR(20),
    email                  VARCHAR(255),
    user_id                UUID UNIQUE              NOT NULL,
    created_on             TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_on             TIMESTAMP WITH TIME ZONE,
    active                 BOOLEAN                  NOT NULL,
    CONSTRAINT fk_user_contact FOREIGN KEY (user_id) REFERENCES user_identity_schema.user_identity (user_id) ON DELETE CASCADE
);

ALTER TABLE user_identity_schema.user_identity OWNER TO identity_user;
ALTER TABLE user_identity_schema.user_credential OWNER TO identity_user;
ALTER TABLE user_identity_schema.user_address OWNER TO identity_user;
ALTER TABLE user_identity_schema.user_contact OWNER TO identity_user;


