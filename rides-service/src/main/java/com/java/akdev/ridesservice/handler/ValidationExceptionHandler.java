package com.java.akdev.ridesservice.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.java.akdev.ridesservice.dto.ErrorResponse;
import com.java.akdev.ridesservice.exception.RideNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String PREFIX = "argumentNotValid.field.";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(PREFIX + fieldName,
                    errorMessage);
        });
        return ResponseEntity.status(400).body(errors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        var cause = (InvalidFormatException) ex.getCause();
        String fieldName = cause.getPath().getFirst().getFieldName();

        errors.put(PREFIX + fieldName, fieldName + ".error");

        return ResponseEntity.status(400).body(errors);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getParameterValidationResults().forEach(error -> {
            Object fieldName = error.getMethodParameter().getParameterName();
            errors.put(PREFIX + fieldName,
                    PREFIX + fieldName + ".mustBePositive");
        });
        return ResponseEntity.status(400).body(errors);
    }

    @ExceptionHandler(RideNotFoundException.class)
    public ResponseEntity<?> handleRideNotFound(RideNotFoundException e) {
        return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        var paramName = ex.getParameterName();
        errors.put(PREFIX + paramName,
                paramName + ".error");
        return ResponseEntity.status(400).body(errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                     WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        var propertyName = ex.getPropertyName();
        errors.put(PREFIX + propertyName,
                PREFIX + propertyName + ".conversionNotSupported");
        return ResponseEntity.status(400).body(errors);
    }
}
