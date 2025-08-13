package com.jyotirmay.useridentity.exception;

import com.jyotirmay.useridentity.dto.GenericErrorResponseTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;

import java.util.Collections;
import java.util.Set;

public class UserIdentityExceptionHandlerTest {

    private final UserIdentityExceptionHandler handler = new UserIdentityExceptionHandler();

    @Test
    @DisplayName("Handle UserNotFoundException returns 404 with proper error response")
    void givenUserNotFoundException_whenHandled_thenReturnsNotFoundResponse() {
        UserNotFoundException ex = new UserNotFoundException("ERR404", "User not found with email test@example.com");

        try (MockedStatic<org.apache.logging.log4j.LogManager> logManagerMock = Mockito.mockStatic(org.apache.logging.log4j.LogManager.class)) {
            // Skipping logger verification due to static final

            ResponseEntity<GenericErrorResponseTO> response = handler.handleUserNotFoundException(ex);

            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            Assertions.assertNotNull(response.getBody());
            Assertions.assertEquals("ERR404", response.getBody().error().errorCode());
            Assertions.assertEquals("User not found with email test@example.com", response.getBody().error().errorMessage());
        }
    }

    @Test
    @DisplayName("Handle UserAlreadyExistsException returns 400 with proper error response")
    void givenUserAlreadyExistsException_whenHandled_thenReturnsBadRequestResponse() {
        UserAlreadyExistsException ex = new UserAlreadyExistsException("ERR400", "User already exists");

        ResponseEntity<GenericErrorResponseTO> response = handler.handleUserAlreadyExistsException(ex);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("ERR400", response.getBody().error().errorCode());
        Assertions.assertEquals("User already exists", response.getBody().error().errorMessage());
    }

    @Test
    @DisplayName("Handle MethodArgumentNotValidException returns 400 with first field error message")
    void givenMethodArgumentNotValidException_whenHandled_thenReturnsBadRequestWithFieldError() {
        FieldError fieldError = new FieldError("objectName", "field", "must not be blank");

        MethodArgumentNotValidException ex = Mockito.mock(MethodArgumentNotValidException.class);
        var bindingResult = Mockito.mock(org.springframework.validation.BindingResult.class);
        Mockito.when(ex.getBindingResult()).thenReturn(bindingResult);
        Mockito.when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        ResponseEntity<GenericErrorResponseTO> response = handler.handleValidationErrors(ex);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("ERR400", response.getBody().error().errorCode());
        Assertions.assertEquals("must not be blank", response.getBody().error().errorMessage());
    }

    @Test
    @DisplayName("Handle MissingServletRequestParameterException returns 400 with missing parameter message")
    void givenMissingServletRequestParameterException_whenHandled_thenReturnsBadRequestWithMessage() {
        MissingServletRequestParameterException ex = new MissingServletRequestParameterException("email", "String");

        ResponseEntity<GenericErrorResponseTO> response = handler.handleMissingParam(ex);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("ERR400", response.getBody().error().errorCode());
        Assertions.assertEquals("Missing required parameter: email", response.getBody().error().errorMessage());
    }

    @Test
    @DisplayName("Handle ConstraintViolationException returns 400 with combined violation messages")
    void givenConstraintViolationException_whenHandled_thenReturnsBadRequestWithCombinedMessages() {
        ConstraintViolation<?> violation1 = Mockito.mock(ConstraintViolation.class);
        ConstraintViolation<?> violation2 = Mockito.mock(ConstraintViolation.class);

        Mockito.when(violation1.getMessage()).thenReturn("must not be null");
        Mockito.when(violation2.getMessage()).thenReturn("size must be between 1 and 10");

        Set<ConstraintViolation<?>> violations = Set.of(violation1, violation2);
        ConstraintViolationException ex = new ConstraintViolationException(violations);

        ResponseEntity<GenericErrorResponseTO> response = handler.handleConstraintViolation(ex);

        String combinedMessages = response.getBody().error().errorMessage();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        // Check if combined message contains both violation messages
        Assertions.assertEquals(true, combinedMessages.contains("must not be null"));
        Assertions.assertEquals(true, combinedMessages.contains("size must be between 1 and 10"));
        Assertions.assertEquals("ERR400", response.getBody().error().errorCode());
    }

    @Test
    @DisplayName("Handle UnsatisfiedServletRequestParameterException returns 400 with message")
    void givenUnsatisfiedServletRequestParameterException_whenHandled_thenReturnsBadRequestWithMessage() {
        UnsatisfiedServletRequestParameterException ex = Mockito.mock(UnsatisfiedServletRequestParameterException.class);
        Mockito.when(ex.getMessage()).thenReturn("some message");

        ResponseEntity<GenericErrorResponseTO> response = handler.handleUnsatisfiedParams(ex);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("ERR400", response.getBody().error().errorCode());
        Assertions.assertEquals(true, response.getBody().error().errorMessage().contains("Missing required query parameter: some message"));
    }
}
