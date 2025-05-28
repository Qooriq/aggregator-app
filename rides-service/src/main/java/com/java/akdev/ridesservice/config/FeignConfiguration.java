package com.java.akdev.ridesservice.config;

import com.java.akdev.ridesservice.handler.EntityExistDecoder;
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
