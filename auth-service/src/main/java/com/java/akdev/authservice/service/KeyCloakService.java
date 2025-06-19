package com.java.akdev.authservice.service;

import com.java.akdev.authservice.dto.TokenResponse;
import com.java.akdev.authservice.dto.UserLogin;
import com.java.akdev.authservice.dto.UserRegistration;
import com.java.akdev.authservice.enumeration.Role;
import com.java.akdev.authservice.kafka.ReviewKafkaSender;
import com.java.akdev.commonmodels.dto.UserRegistrationCreateDto;
import com.java.akdev.commonmodels.dto.UserResponse;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class KeyCloakService {
    @Value("${keycloak.auth-server-url}")
    private String serverUrl;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.client-secret}")
    private String clientSecret;
    private final Keycloak keycloak;
    private final PasswordEncoder passwordEncoder;
    private final ReviewKafkaSender sender;

    public UserResponse registration(UserRegistration registration, Role userRole) {
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        var creds = new CredentialRepresentation();
        creds.setType(CredentialRepresentation.PASSWORD);
        creds.setValue(registration.password());
        creds.setTemporary(false);

        UserRepresentation user = new UserRepresentation();
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("firstName", List.of(registration.firstName()));
        attributes.put("lastName", List.of(registration.lastName()));
        attributes.put("phoneNumber", List.of(registration.phoneNumber()));
        user.setAttributes(attributes);
        user.setUsername(registration.username());
        user.setEnabled(true);
        user.setEmail(registration.username());
        user.setEmailVerified(true);
        user.setCredentials(Collections.singletonList(
                creds
        ));

        Response response = usersResource.create(user);

        if (response.getStatus() != 201) {
            throw new RuntimeException("error.UserCreation");
        }

        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
        UserResource userResource = usersResource.get(userId);

        RoleRepresentation role = realmResource.roles().get(String.valueOf(userRole)).toRepresentation();
        userResource.roles().realmLevel().add(Collections.singletonList(role));

        sender.sendPassengerCreate(new UserRegistrationCreateDto(UUID.fromString(userId), registration.firstName(),
                registration.lastName(), registration.username(), passwordEncoder.encode(registration.password()),
                registration.phoneNumber()), userRole);
        response.close();
        return new UserResponse(registration.firstName(), registration.lastName(), registration.username());
    }

    public TokenResponse login(UserLogin loginDto) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(loginDto.username())
                .password(loginDto.password())
                .grantType("password")
                .build();

        AccessTokenResponse response = keycloak.tokenManager().getAccessToken();
        return new TokenResponse(response.getToken());
    }
}
