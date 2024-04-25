package com.example.flightmanagement.shared.exception.handler;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Map<String, List<String>>> handleIllegalStateException(IllegalStateException exception) {
        return createResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, List<String>>> handleIllegalArgumentException(
            IllegalArgumentException exception
    ) {
        return createResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, List<String>>> handleNoSuchElementException(NoSuchElementException exception) {
        return createResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, List<String>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        final var errors = exception.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .toList();
        return createResponse(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, List<String>>> handleValidationException(ValidationException exception) {
        return createResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, List<String>>> handleException(Exception exception) {
        return createResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Map<String, List<String>>> createResponse(List<String> errors, HttpStatus status) {
        final var content = Map.of("errors", errors);
        return ResponseEntity.status(status).body(content);
    }

    private ResponseEntity<Map<String, List<String>>> createResponse(String message, HttpStatus status) {
        return createResponse(List.of(message), status);
    }
}
