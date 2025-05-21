package com.java.akdev.walletservice.repository;

import com.java.akdev.walletservice.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, Long>,
        RevisionRepository<Wallet, Long, Long> {

    Wallet findByUserId(UUID userId);
}
