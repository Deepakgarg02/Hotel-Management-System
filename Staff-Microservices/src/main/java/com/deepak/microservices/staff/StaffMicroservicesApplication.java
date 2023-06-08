package com.deepak.microservices.staff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StaffMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(StaffMicroservicesApplication.class, args);
	}

}
