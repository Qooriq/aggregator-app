package com.java.akdev.passengerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class TestPassengerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(PassengerServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
