package com.betalyze.shared.infrastructure.exception;

import com.betalyze.shared.application.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<Void>> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(Response.error(ex.getMessage()));
    }
    
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Response<Void>> handleDomainException(DomainException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Response.error(ex.getMessage()));
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Response<Void>> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(Response.error("Invalid credentials"));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Map<String, String>>> handleValidationExceptions(
        MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Response.error("Validation failed", errors));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Void>> handleGenericException(Exception ex) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Response.error("An unexpected error occurred"));
    }
}
