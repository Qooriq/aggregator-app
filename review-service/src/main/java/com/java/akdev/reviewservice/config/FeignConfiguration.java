package com.java.akdev.reviewservice.config;

import com.java.akdev.reviewservice.client.interceptor.OAuth2RequestInterceptor;
import com.java.akdev.reviewservice.handler.EntityExistDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new EntityExistDecoder();
    }

    @Bean
    public OAuth2RequestInterceptor oAuth2RequestInterceptor() {
        return new OAuth2RequestInterceptor();
    }
}
