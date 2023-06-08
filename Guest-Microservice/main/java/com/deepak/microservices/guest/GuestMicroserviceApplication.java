package com.deepak.microservices.guest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class GuestMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuestMicroserviceApplication.class, args);
	}

}
