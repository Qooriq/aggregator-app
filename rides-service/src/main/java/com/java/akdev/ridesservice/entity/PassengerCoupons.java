package com.java.akdev.ridesservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "passengers_coupons")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class PassengerCoupons {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "passenger_id")
    private UUID passengerId;

    @ManyToOne
    @JoinColumn(name = "coupon_name", referencedColumnName = "coupon")
    private Coupon coupon;
}
