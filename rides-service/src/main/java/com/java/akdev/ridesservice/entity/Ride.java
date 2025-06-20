package com.java.akdev.ridesservice.entity;

import com.java.akdev.ridesservice.enumeration.PaymentMethod;
import com.java.akdev.ridesservice.enumeration.RideStatus;
import com.java.akdev.ridesservice.enumeration.RideType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "rides")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Ride {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "passenger_id", nullable = false)
    private UUID passengerId;

    @Column(name = "driver_id")
    private UUID driverId;

    @Column(name = "passenger_review_driver")
    private Long driverReviewPassenger;

    @Column(name = "driver_review_passenger")
    private Long passengerReviewDriver;

    @Column(name = "start_location", nullable = false)
    private String startLocation;

    @Column(name = "end_location", nullable = false)
    private String endLocation;

    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Column(name = "ride_price")
    private Double ridePrice;

    @Column(name = "ride_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RideStatus status;

    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "ride_type", nullable = false)
    private RideType rideType;
}
