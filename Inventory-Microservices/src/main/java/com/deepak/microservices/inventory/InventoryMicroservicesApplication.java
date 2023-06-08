package com.deepak.microservices.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class InventoryMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryMicroservicesApplication.class, args);
	}

}
