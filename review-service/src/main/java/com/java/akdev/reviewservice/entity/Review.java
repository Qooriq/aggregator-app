package com.java.akdev.reviewservice.entity;

import com.java.akdev.reviewservice.enumeration.Receiver;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "reviews")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "passenger_id", nullable = false)
    private UUID passengerId;

    @Column(name = "driver_id", nullable = false)
    private UUID driverId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "review", nullable = false)
    private Short review;

    @Column(name = "receiver_of_review", nullable = false)
    @Enumerated(EnumType.STRING)
    private Receiver receiver;

    @Column(name = "ride_id", nullable = false)
    private Long rideId;
}
