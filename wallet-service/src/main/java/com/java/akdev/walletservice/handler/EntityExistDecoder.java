package com.java.akdev.walletservice.handler;

import com.java.akdev.walletservice.exception.UserNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class EntityExistDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        switch (response.status()) {
            case 404:
                return new UserNotFoundException("WalletController.entity.notFound");
            default:
                return new RuntimeException("RideController.api.error");
        }
    }
}
