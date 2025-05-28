package com.java.akdev.gatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayConfiguration {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("driver-service", r -> r.path("/api/v1/driver-service/**")
                        .uri("lb://driver-service"))
                .route("passenger-service", r -> r.path("/api/v1/passenger-service/**")
                        .uri("lb://passenger-service"))
                .route("rides-service", r -> r.path("/api/v1/rides-service/**")
                        .uri("lb://rides-service"))
                .route("wallet-service", r -> r.path("/api/v1/wallets/**")
                        .uri("lb://wallet-service"))
                .route("review-service", r -> r.path("/api/v1/reviews/**")
                        .uri("lb://review-service"))
                .build();
    }
}
