package com.java.akdev.passengerservice.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.java.akdev.passengerservice.controller")
@RequiredArgsConstructor
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String PREFIX = "PassengerController.field.";

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(messageSource.getMessage(PREFIX + fieldName, null, request.getLocale()),
                    messageSource.getMessage(errorMessage, null, request.getLocale()));
        });
        return ResponseEntity.status(400).body(errors);
    }
}
