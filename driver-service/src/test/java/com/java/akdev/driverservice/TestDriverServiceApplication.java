package com.java.akdev.driverservice;

import org.springframework.boot.SpringApplication;

public class TestDriverServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(DriverServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
