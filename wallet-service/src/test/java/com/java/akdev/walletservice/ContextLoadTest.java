package com.java.akdev.walletservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ContextLoadTest extends IntegrationTestBase {

    @Test
    void contextLoads() {}
}
