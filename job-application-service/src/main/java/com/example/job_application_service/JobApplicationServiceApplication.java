package com.example.job_application_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JobApplicationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobApplicationServiceApplication.class, args);
	}

}
