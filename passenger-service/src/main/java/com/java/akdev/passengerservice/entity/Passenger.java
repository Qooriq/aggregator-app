package com.java.akdev.passengerservice.entity;

import com.java.akdev.passengerservice.enumeration.PassengerStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "passengers")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@ToString
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @OneToMany
    @JoinColumn(name = "passenger_id")
    @ToString.Exclude
    @Builder.Default
    private List<PassengerRating> passengerRatings = new ArrayList<>();

    @Column(name = "passenger_status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PassengerStatus status = PassengerStatus.ACTIVE;
}
