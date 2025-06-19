package com.java.akdev.ridesservice.handler;

import com.java.akdev.ridesservice.exception.EntityNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class EntityExistDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        switch (response.status()) {
            case 404:
                return new EntityNotFoundException("RideController.entity.notFound");
            case 403:
                return new RuntimeException("Forbidden");
            default:
                return new RuntimeException("RideController.api.error");
        }
    }
}
