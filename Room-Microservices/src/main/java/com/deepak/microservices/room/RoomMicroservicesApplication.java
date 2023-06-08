package com.deepak.microservices.room;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RoomMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomMicroservicesApplication.class, args);
	}

}
