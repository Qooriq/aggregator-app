package com.java.akdev.walletservice.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(basePackages = "com.java.akdev.walletservice.controller")
@RequiredArgsConstructor
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;
    private final static String PREFIX = "WalletController.field.";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(messageSource.getMessage(PREFIX + fieldName, null, request.getLocale()),
                    messageSource.getMessage(errorMessage, null, request.getLocale()));
        });
        return ResponseEntity.status(400).body(errors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        var cause = ex.getCause();
        if (cause instanceof InvalidFormatException invalidFormatException) {
            String fieldName = invalidFormatException.getPath().getFirst().getFieldName();

            errors.put(messageSource.getMessage(PREFIX + fieldName, null, request.getLocale()),
                    messageSource.getMessage(fieldName + ".error", null, request.getLocale()));
        }
        return ResponseEntity.status(400).body(errors);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getParameterValidationResults().forEach(error -> {
            Object fieldName = error.getMethodParameter().getParameterName();
            errors.put(messageSource.getMessage(PREFIX + fieldName, null, request.getLocale()),
                    messageSource.getMessage(PREFIX + fieldName + ".mustBePositive", null, request.getLocale()
                    ));
        });
        return ResponseEntity.status(400).body(errors);
    }
}
