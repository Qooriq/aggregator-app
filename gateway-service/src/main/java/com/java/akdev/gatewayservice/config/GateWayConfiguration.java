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
                .route("driver-service", r -> r.path("/api/v1/drivers/**")
                        .uri("lb://DRIVER-SERVICE"))
                .route("passenger-service", r -> r.path("/api/v1/passengers/**")
                        .uri("lb://PASSENGER-SERVICE"))
                .route("rides-service", r -> r.path("/api/v1/rides/**")
                        .uri("lb://RIDES-SERVICE"))
                .route("wallet-service", r -> r.path("/api/v1/wallets/**")
                        .uri("lb://WALLET-SERVICE"))
                .route("review-service", r -> r.path("/api/v1/reviews/**")
                        .uri("lb://REVIEW-SERVICE"))
                .route("authentication", r -> r.path("/api/v1/auth/**")
                        .uri("lb://AUTH-SERVICE"))
                .build();
    }
}
