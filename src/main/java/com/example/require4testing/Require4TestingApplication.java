package com.example.require4testing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.require4testing", "require4testing"})
public class Require4TestingApplication {

    public static void main(String[] args) {
        SpringApplication.run(Require4TestingApplication.class, args);
    }

}
