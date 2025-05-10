package com.java.akdev.reviewservice;

import com.java.akdev.reviewservice.config.AppConfiguration;
import com.java.akdev.reviewservice.config.ArtemisConfiguration;
import com.java.akdev.reviewservice.config.JpaTransactionConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Import({JpaTransactionConfiguration.class, ArtemisConfiguration.class})
@EnableConfigurationProperties(
        {
                AppConfiguration.class
        }
)
public class ReviewServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReviewServiceApplication.class, args);
    }

}
