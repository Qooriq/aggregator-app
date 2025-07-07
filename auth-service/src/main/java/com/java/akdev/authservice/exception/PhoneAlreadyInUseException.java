package com.java.akdev.authservice.exception;

public class PhoneAlreadyInUseException extends RuntimeException {

    public PhoneAlreadyInUseException(String message) {
        super(message);
    }
}
