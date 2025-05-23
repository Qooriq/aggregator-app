package com.java.akdev.driverservice.handler;

import com.java.akdev.driverservice.enumeration.ExceptionMessages;
import com.java.akdev.driverservice.exception.DriverNotFoundException;
import com.java.akdev.driverservice.exception.PhoneAlreadyExistsException;
import com.java.akdev.driverservice.exception.UsernameAlreadyExistsException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
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
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex,
                                                                            @NonNull HttpHeaders headers,
                                                                            @NonNull HttpStatusCode status,
                                                                            @NonNull WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getParameterValidationResults().forEach(error -> {
            String fieldName = error.getMethodParameter().getParameterName();
            errors.put(fieldName,
                    messageSource.getMessage(ExceptionMessages.FIELD_MUST_PRESENT.getName(),
                            new Object[]{fieldName}, request.getLocale()
                    ));
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                     WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        var propertyName = ex.getPropertyName();
        errors.put(propertyName,
                messageSource.getMessage(ExceptionMessages.WRONG_TYPE.getName(),
                        new Object[]{propertyName}, request.getLocale()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(DriverNotFoundException.class)
    public ResponseEntity<Object> handleWalletNotFound(DriverNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("id",
                messageSource.getMessage(ex.getMessage(), null, request.getLocale()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(PhoneAlreadyExistsException.class)
    public ResponseEntity<Object> handlePhoneAlreadyExists(PhoneAlreadyExistsException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("phoneNumber",
                messageSource.getMessage(ex.getMessage(), new Object[]{"phoneNumber"}, request.getLocale()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Object> handleUsernameAlreadyExists(PhoneAlreadyExistsException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("username",
                messageSource.getMessage(ex.getMessage(), new Object[]{"username"}, request.getLocale()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
