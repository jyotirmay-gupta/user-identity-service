package com.jyotirmay.useridentity.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class IdentityUtilsTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    private IdentityUtils identityUtils;

    @BeforeEach
    void setUp() {
        identityUtils = new IdentityUtils(passwordEncoder);
    }

    @Test
    @DisplayName("Should return encoded password when encryptPassword is called with a raw password")
    void givenPlainPassword_whenEncryptPasswordCalled_thenReturnsEncodedPassword() {

        String rawPassword = "mySecret123!";
        String encodedPassword = "$2a$10$SomeHashedValue";

        Mockito.when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        String actual = identityUtils.encryptPassword(rawPassword);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(encodedPassword, actual);
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(rawPassword);
    }

    @Test
    @DisplayName("Should generate a 12-character password containing uppercase, lowercase, digit, and special characters")
    void whenGenerateRandomPasswordCalled_thenReturnsValidLengthAndCharacterTypes() {

        String generatedPassword = identityUtils.generateRandomPassword();

        Assertions.assertNotNull(generatedPassword);
        Assertions.assertEquals(12, generatedPassword.length());
    }
}
