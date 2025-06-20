package com.java.akdev.ridesservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "coupons")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "coupon")
    private String coupon;

    @Column(nullable = false, name = "discount")
    private Double discount;
}
