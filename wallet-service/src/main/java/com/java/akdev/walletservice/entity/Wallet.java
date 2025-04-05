package com.java.akdev.walletservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.util.UUID;

@Entity
@Table(name = "wallets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotAudited
    @Column(name = "card_number", nullable = false, length = 16)
    private String cardNumber;

    @Builder.Default
    @Column(name = "amount", nullable = false)
    private Double amount = 0.0;

    @NotAudited
    @Column(name = "user_id", nullable = false)
    private UUID userId;
}
