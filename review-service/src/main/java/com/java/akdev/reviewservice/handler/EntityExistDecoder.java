package com.java.akdev.reviewservice.handler;

import com.java.akdev.reviewservice.exception.EntityNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class EntityExistDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        switch (response.status()) {
            case 404:
                return new EntityNotFoundException("ReviewController.entity.notFound");
            default:
                return new RuntimeException("RideController.api.error");
        }
    }
}
