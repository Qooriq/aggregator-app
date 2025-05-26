package com.java.akdev.passengerservice.handler;

import com.java.akdev.passengerservice.enumeration.ExceptionMessages;
import com.java.akdev.passengerservice.exception.PassengerNotFoundException;
import com.java.akdev.passengerservice.exception.PhoneNumberAlreadyExistsException;
import com.java.akdev.passengerservice.exception.UsernameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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

@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "com.java.akdev.passengerservice.controller")
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
                    messageSource.getMessage(errorMessage, new Object[]{fieldName}, request.getLocale()));
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex,
                                                                            @NonNull HttpHeaders headers,
                                                                            @NonNull HttpStatusCode status,
                                                                            @NonNull WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getParameterValidationResults().forEach(error -> {
            Object fieldName = error.getMethodParameter().getParameterName();
            errors.put(fieldName.toString(),
                    messageSource.getMessage(ExceptionMessages.MUST_BE_POSITIVE.getName(), new Object[]{fieldName}, request.getLocale()
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
                messageSource.getMessage(ExceptionMessages.FIELD_MUST_PRESENT.getName(), new Object[]{paramName}, request.getLocale()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                     WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        var propertyName = ex.getPropertyName();
        errors.put(propertyName,
                messageSource.getMessage(ExceptionMessages.WRONG_TYPE.getName(), new Object[]{propertyName}, request.getLocale()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(PassengerNotFoundException.class)
    public ResponseEntity<Object> handlePassengerNotFoundException(PassengerNotFoundException ex,
                                                                   WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("id",
                messageSource.getMessage(ex.getMessage(), null, request.getLocale()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Object> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex,
                                                                       WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("username",
                messageSource.getMessage(ex.getMessage(), new Object[]{"username"}, request.getLocale()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(PhoneNumberAlreadyExistsException.class)
    public ResponseEntity<Object> handleUsernameAlreadyExistsException(PhoneNumberAlreadyExistsException ex,
                                                                       WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("phoneNumber",
                messageSource.getMessage(ex.getMessage(), new Object[]{"phoneNumber"}, request.getLocale()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
