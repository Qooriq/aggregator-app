package com.java.akdev.reviewservice.client.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;

public class OAuth2RequestInterceptor implements RequestInterceptor {

    private String accessToken;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
    }
}
