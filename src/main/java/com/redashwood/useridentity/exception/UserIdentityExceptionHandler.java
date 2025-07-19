package com.redashwood.useridentity.exception;

import com.redashwood.useridentity.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.redashwood.useridentity.util.UserIdentityConstants.*;

@ControllerAdvice
public class UserIdentityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {

        ErrorTO errorTO = new ErrorTO(ex.getErrorCode(), ex.getMessage());

        String className = ex.getStackTrace().length > 0 ? ex.getStackTrace()[0].getClassName() : null;
        if (className == null) {
            return new ResponseEntity<>(errorTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            String simpleClassName = Class.forName(className).getSimpleName();
            return switch (simpleClassName) {
                case REGISTER_USER_SERVICE ->
                        new ResponseEntity<>(new RegisterUserResponseTO(null, null, errorTO), HttpStatus.NOT_FOUND);
                case UPDATE_USER_SERVICE ->
                        new ResponseEntity<>(new UpdateUserResponseTO(null, errorTO), HttpStatus.NOT_FOUND);
                case GET_USER_SERVICE ->
                        new ResponseEntity<>(new GetUserResponseTO(null, errorTO), HttpStatus.NOT_FOUND);
                case DELETE_USER_SERVICE ->
                        new ResponseEntity<>(new DeleteUserResponseTO(null, errorTO), HttpStatus.NOT_FOUND);
                case UPDATE_USER_CREDENTIAL_SERVICE ->
                        new ResponseEntity<>(new UpdateCredentialResponseTO(null, errorTO), HttpStatus.NOT_FOUND);
                default -> new ResponseEntity<>(errorTO, HttpStatus.INTERNAL_SERVER_ERROR);
            };

        } catch (ClassNotFoundException e) {
            return new ResponseEntity<>(errorTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {

        FieldError fieldError = ex.getBindingResult().getFieldErrors().getFirst();
        ErrorTO errorTO = new ErrorTO("ERR400", fieldError.getDefaultMessage());

        String className = ex.getStackTrace().length > 0 ? ex.getStackTrace()[0].getClassName() : null;
        if (className == null) {
            return new ResponseEntity<>(errorTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            String simpleClassName = Class.forName(className).getSimpleName();
            return switch (simpleClassName) {
                case REGISTER_USER_SERVICE ->
                        new ResponseEntity<>(new RegisterUserResponseTO(null, null, errorTO), HttpStatus.BAD_REQUEST);
                case UPDATE_USER_SERVICE ->
                        new ResponseEntity<>(new UpdateUserResponseTO(null, errorTO), HttpStatus.BAD_REQUEST);
                case GET_USER_SERVICE ->
                        new ResponseEntity<>(new GetUserResponseTO(null, errorTO), HttpStatus.BAD_REQUEST);
                case DELETE_USER_SERVICE ->
                        new ResponseEntity<>(new DeleteUserResponseTO(null, errorTO), HttpStatus.BAD_REQUEST);
                case UPDATE_USER_CREDENTIAL_SERVICE ->
                        new ResponseEntity<>(new UpdateCredentialResponseTO(null, errorTO), HttpStatus.BAD_REQUEST);
                default -> new ResponseEntity<>(errorTO, HttpStatus.INTERNAL_SERVER_ERROR);
            };

        } catch (ClassNotFoundException e) {
            return new ResponseEntity<>(errorTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
