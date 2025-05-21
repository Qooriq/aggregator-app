package com.java.akdev.reviewservice.client;

import com.java.akdev.commonmodels.dto.RideResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ride-feign-client", url = "localhost:8084/api/v1/rides")
public interface CheckRideExistClient {

    @GetMapping("/{id}")
    ResponseEntity<RideResponse> findRideById(@PathVariable("id") Long id);
}
