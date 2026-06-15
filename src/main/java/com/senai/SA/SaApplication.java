package com.senai.SA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaApplication.class, args);
    }

}
