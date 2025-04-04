package com.java.akdev.walletservice.repository;

import com.java.akdev.walletservice.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long>,
        RevisionRepository<Wallet, Long, Long> {
}
