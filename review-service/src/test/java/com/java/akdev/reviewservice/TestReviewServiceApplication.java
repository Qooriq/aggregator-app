package com.java.akdev.reviewservice;

import org.springframework.boot.SpringApplication;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class TestReviewServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(ReviewServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
