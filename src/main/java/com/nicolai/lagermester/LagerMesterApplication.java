package com.nicolai.lagermester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // Aktiverer støtte for @Scheduled
public class LagerMesterApplication {

    public static void main(String[] args) {
        SpringApplication.run(LagerMesterApplication.class, args);
    }
}
