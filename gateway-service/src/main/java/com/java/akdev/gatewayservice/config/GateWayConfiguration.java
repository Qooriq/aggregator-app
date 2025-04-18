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
                        .uri("http://localhost:8081/"))
                .route("passenger-service", r -> r.path("/api/v1/passenger-service/**")
                        .uri("http://localhost:8082/"))
                .route("rides-service", r -> r.path("/api/v1/rides-service/**")
                        .uri("http://localhost:8083/"))
                .build();
    }
}
