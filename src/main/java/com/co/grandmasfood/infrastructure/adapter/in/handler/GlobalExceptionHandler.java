package com.co.grandmasfood.infrastructure.adapter.in.handler;

import com.co.grandmasfood.domain.exception.Client.*;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleClientAlreadyExists(
            ClientAlreadyExistsException ex) {

        log.error("Client already exists: {}", ex.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .code(ex.getErrorCode())
                .timestamp(LocalDateTime.now())
                .description(ex.getMessage())
                .exception(null)
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler({InvalidDocumentException.class, InvalidEmailException.class})
    public ResponseEntity<ErrorResponse> handleInvalidValueObject(
            DomainException ex) {

        log.error("Invalid value object: {}", ex.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .code(ex.getErrorCode())
                .timestamp(LocalDateTime.now())
                .description(ex.getMessage())
                .exception(null)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            WebExchangeBindException ex) {

        log.error("Validation error: {}", ex.getMessage());

        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse response = ErrorResponse.builder()
                .code("VALIDATION_ERROR")
                .timestamp(LocalDateTime.now())
                .description("Invalid or incomplete client data: " + errors)
                .exception(null)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Internal server error", ex);

        ErrorResponse response = ErrorResponse.builder()
                .code("INTERNAL_SERVER_ERROR")
                .timestamp(LocalDateTime.now())
                .description("An unexpected error occurred")
                .exception(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClientNotFound(ClientNotFoundException ex) {
        log.error("Client not found: {}", ex.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .code(ex.getErrorCode())        // "CLIENT_NOT_FOUND"
                .timestamp(LocalDateTime.now())
                .description(ex.getMessage())   // "Client with document CC-123456 not found"
                .exception(null)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        // Retorna: HTTP 404 NOT FOUND
    }
}