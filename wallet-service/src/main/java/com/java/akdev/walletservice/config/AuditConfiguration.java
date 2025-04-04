package com.java.akdev.walletservice.config;

import com.java.akdev.walletservice.WalletServiceApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@EnableEnversRepositories(basePackageClasses = WalletServiceApplication.class)
public class AuditConfiguration {
}
