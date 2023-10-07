package com.example.ingda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class IngdaApplication {

    public static void main(String[] args) {
        SpringApplication.run(IngdaApplication.class, args);
    }

}
