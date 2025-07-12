package com.java.akdev.ridesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class TestRidesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(RidesServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
