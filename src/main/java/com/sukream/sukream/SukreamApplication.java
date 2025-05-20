package com.sukream.sukream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SukreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(SukreamApplication.class, args);
    }

}
