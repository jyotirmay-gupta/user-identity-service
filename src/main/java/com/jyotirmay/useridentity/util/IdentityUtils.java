package com.jyotirmay.useridentity.util;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

public class IdentityUtils {

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = UPPER.toLowerCase();
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%&*()-_=+<>?";
    private static final String ALL_CHARS = UPPER + LOWER + DIGITS + SPECIAL;
    private static final SecureRandom random = new SecureRandom();
    private static final int PASSWORD_LENGTH = 12;

    private final PasswordEncoder passwordEncoder;

    public IdentityUtils(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public String generateRandomPassword() {

        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length())));
        }
        return password.toString();
    }
}
