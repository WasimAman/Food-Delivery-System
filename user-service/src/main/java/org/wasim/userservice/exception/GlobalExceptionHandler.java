package org.wasim.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.authentication.BadCredentialsException;
import org.wasim.userservice.dto.response.ExceptionResponse;
import org.wasim.userservice.dto.response.ExceptionStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse<Map<String,String>>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ExceptionResponse<Map<String,String>> response = new ExceptionResponse<>();

        response.setSuccess(false);
        response.setMessage("Validation failed");
        response.setStaus(ExceptionStatus.BAD_REQUEST);
        response.setErrors(errors);
        response.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Invalid Role
    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<?> handleInvalidRoleException(
            InvalidRoleException ex) {

        ExceptionResponse<String> response = new ExceptionResponse<>();
        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        response.setStaus(ExceptionStatus.BAD_REQUEST);
        response.setErrors("Invalid role");
        response.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // User Already Exists
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExists(
            UserAlreadyExistsException ex) {

        ExceptionResponse<String> response = new ExceptionResponse<>();
        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        response.setStaus(ExceptionStatus.USER_CONFLICT);
        response.setErrors("User already present");
        response.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // User Not Found
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(
            UserNotFoundException ex) {

        ExceptionResponse<String> response = new ExceptionResponse<>();
        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        response.setStaus(ExceptionStatus.NOT_FOUND);
        response.setErrors("not found");
        response.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Login – Wrong Credentials
    @ExceptionHandler({BadCredentialsException.class, RuntimeException.class})
    public ResponseEntity<?> handleBadCredentials(
            Exception ex) {

        ExceptionResponse<String> response = new ExceptionResponse<>();
        response.setSuccess(false);
        response.setMessage("Invalid email or password");
        response.setStaus(ExceptionStatus.UNAUTHORIZED);
        response.setErrors("not found");
        response.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}