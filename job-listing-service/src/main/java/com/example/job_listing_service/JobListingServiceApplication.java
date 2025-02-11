package com.example.job_listing_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JobListingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobListingServiceApplication.class, args);
    }

}
