package com.jyotirmay.useridentity.exception;

import com.jyotirmay.useridentity.dto.ErrorTO;
import com.jyotirmay.useridentity.dto.GenericErrorResponseTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import static com.jyotirmay.useridentity.util.UserIdentityConstants.BAD_REQUEST_ERROR_CODE;

@RestControllerAdvice
public class UserIdentityExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(UserIdentityExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<GenericErrorResponseTO> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorTO errorTO = new ErrorTO(ex.getErrorCode(), ex.getMessage());
        GenericErrorResponseTO genericErrorResponseTO = new GenericErrorResponseTO(errorTO);
        logErrorMessage(errorTO);
        return new ResponseEntity<>(genericErrorResponseTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<GenericErrorResponseTO> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ErrorTO errorTO = new ErrorTO(ex.getErrorCode(), ex.getMessage());
        GenericErrorResponseTO genericErrorResponseTO = new GenericErrorResponseTO(errorTO);
        logErrorMessage(errorTO);
        return new ResponseEntity<>(genericErrorResponseTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericErrorResponseTO> handleValidationErrors(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldErrors().getFirst();
        ErrorTO errorTO = new ErrorTO(BAD_REQUEST_ERROR_CODE, fieldError.getDefaultMessage());
        GenericErrorResponseTO genericErrorResponseTO = new GenericErrorResponseTO(errorTO);
        logErrorMessage(errorTO);
        return new ResponseEntity<>(genericErrorResponseTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<GenericErrorResponseTO> handleMissingParam(MissingServletRequestParameterException ex) {
        ErrorTO errorTO = new ErrorTO(BAD_REQUEST_ERROR_CODE, "Missing required parameter: " + ex.getParameterName());
        GenericErrorResponseTO genericErrorResponseTO = new GenericErrorResponseTO(errorTO);
        logErrorMessage(errorTO);
        return new ResponseEntity<>(genericErrorResponseTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GenericErrorResponseTO> handleConstraintViolation(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
        ErrorTO errorTO = new ErrorTO(BAD_REQUEST_ERROR_CODE, message);
        GenericErrorResponseTO genericErrorResponseTO = new GenericErrorResponseTO(errorTO);
        logErrorMessage(errorTO);
        return new ResponseEntity<>(genericErrorResponseTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public ResponseEntity<GenericErrorResponseTO> handleUnsatisfiedParams(UnsatisfiedServletRequestParameterException ex) {

        ErrorTO errorTO = new ErrorTO(BAD_REQUEST_ERROR_CODE, "Missing required query parameter: " + ex.getMessage());
        GenericErrorResponseTO genericErrorResponseTO = new GenericErrorResponseTO(errorTO);
        logErrorMessage(errorTO);
        return new ResponseEntity<>(genericErrorResponseTO, HttpStatus.BAD_REQUEST);
    }

    private static void logErrorMessage(ErrorTO errorTO) {
        LOGGER.error("Exception occurred while processing the request:: #{}: {}", errorTO.errorCode(), errorTO.errorMessage());
    }
}
