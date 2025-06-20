package com.java.akdev.authservice.controller;

import com.java.akdev.authservice.dto.TokenResponse;
import com.java.akdev.authservice.dto.UserLogin;
import com.java.akdev.authservice.dto.UserRegistration;
import com.java.akdev.authservice.enumeration.Role;
import com.java.akdev.authservice.service.KeyCloakService;
import com.java.akdev.commonmodels.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final KeyCloakService keyCloakService;

    @PostMapping("/driver")
    public ResponseEntity<UserResponse> driver(@RequestBody UserRegistration registration) {
        return ResponseEntity.status(201)
                .body(keyCloakService.registration(registration, Role.DRIVER));
    }

    @PostMapping("/passenger")
    public ResponseEntity<UserResponse> passenger(@RequestBody UserRegistration registration) {
        return ResponseEntity.status(201)
                .body(keyCloakService.registration(registration, Role.PASSENGER));
    }

    @GetMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody UserLogin login) {
        return ResponseEntity.status(200)
                .body(keyCloakService.login(login));
    }
}
