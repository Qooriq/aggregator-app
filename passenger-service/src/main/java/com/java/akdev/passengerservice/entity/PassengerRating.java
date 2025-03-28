package com.java.akdev.passengerservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "passenger_rating")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PassengerRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Passenger passenger;

    @Column(nullable = false)
    private Long review;
}
