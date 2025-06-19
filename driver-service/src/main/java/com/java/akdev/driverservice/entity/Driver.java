package com.java.akdev.driverservice.entity;

import com.java.akdev.driverservice.enumeration.DriverStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "drivers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Driver {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    @ToString.Include
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @ToString.Include
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ToString.Include
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Builder.Default
    @Column(name = "rating", nullable = false)
    private Double rating = 5.0;

    @Builder.Default
    @Column(name = "driver_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DriverStatus status = DriverStatus.ACTIVE;
}
