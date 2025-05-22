package com.java.akdev.reviewservice.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.java.akdev.reviewservice.enumeration.ExceptionMessages;
import com.java.akdev.reviewservice.exception.EntityNotFoundException;
import com.java.akdev.reviewservice.exception.ReviewNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
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
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.java.akdev.reviewservice.controller")
@RequiredArgsConstructor
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName,
                    messageSource.getMessage(errorMessage,
                            new Object[]{fieldName}, request.getLocale()));
        });
        return ResponseEntity.status(400).body(errors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        var cause = (InvalidFormatException) ex.getCause();
        String fieldName = cause.getTargetType().getSimpleName();

        errors.put(fieldName,
                messageSource.getMessage(ExceptionMessages.WRONG_TYPE.getName(),
                        new Object[]{fieldName}, request.getLocale()));

        return ResponseEntity.status(400).body(errors);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          @NonNull HttpHeaders headers,
                                                                          @NonNull HttpStatusCode status,
                                                                          @NonNull WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        var paramName = ex.getParameterName();
        errors.put(paramName,
                messageSource.getMessage(ExceptionMessages.FIELD_MUST_PRESENT.getName(),
                        new Object[]{paramName}, request.getLocale()));
        return ResponseEntity.status(400).body(errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                     WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        var propertyName = ex.getPropertyName();
        errors.put(propertyName,
                messageSource.getMessage(ExceptionMessages.WRONG_TYPE.getName(),
                        new Object[]{propertyName}, request.getLocale()));
        return ResponseEntity.status(400).body(errors);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex,
                                                                            @NonNull HttpHeaders headers,
                                                                            @NonNull HttpStatusCode status,
                                                                            @NonNull WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getParameterValidationResults().forEach(error -> {
            String fieldName = error.getMethodParameter().getParameterName();
            errors.put(fieldName,
                    messageSource.getMessage(ExceptionMessages.MUST_BE_POSITIVE.getName(),
                            new Object[]{fieldName}, request.getLocale()
                    ));
        });
        return ResponseEntity.status(400).body(errors);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<Object> handleReviewNotFoundException(ReviewNotFoundException ex,
                                                                WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("id",
                messageSource.getMessage(ex.getMessage(), null, request.getLocale()));
        return ResponseEntity.status(404).body(errors);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex,
                                                                WebRequest request){
        Map<String, String> errors = new HashMap<>();
        Locale locale = request.getLocale();
        String message = messageSource.getMessage(ex.getMessage(), new Object[]{"entity"}, locale);
        errors.put("entity", message);
        return ResponseEntity.status(404).body(errors);
    }
}
