package com.java.akdev.walletservice.config;

import com.java.akdev.walletservice.handler.EntityExistDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new EntityExistDecoder();
    }
}
