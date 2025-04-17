package com.java.akdev.driverservice.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.java.akdev.driverservice.exception.DriverNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String PREFIX = "DriverController.field.";

    private final MessageSource messageSource;

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
        var cause = (InvalidFormatException) ex.getCause();
        String fieldName = cause.getPath().getFirst().getFieldName();

        errors.put(messageSource.getMessage(PREFIX + fieldName, null, request.getLocale()),
                messageSource.getMessage(fieldName + ".error", null, request.getLocale()));

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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        var paramName = ex.getParameterName();
        errors.put(messageSource.getMessage(PREFIX + paramName, null, request.getLocale()),
                messageSource.getMessage(paramName + ".error", null, request.getLocale()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                     WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        var propertyName = ex.getPropertyName();
        errors.put(messageSource.getMessage(PREFIX + propertyName, null, request.getLocale()),
                messageSource.getMessage(PREFIX + propertyName + ".conversionNotSupported", null, request.getLocale()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(DriverNotFoundException.class)
    public ResponseEntity<Object> handleWalletNotFound(DriverNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put(messageSource.getMessage(PREFIX + "id", null, request.getLocale()),
                messageSource.getMessage(ex.getMessage(), null, request.getLocale()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
