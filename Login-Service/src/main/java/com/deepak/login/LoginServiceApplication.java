package com.deepak.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.deepak.login.model.EnumRole;
import com.deepak.login.model.Roles;
import com.deepak.login.repository.RoleRepo;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
//@EnableEurekaClient
@EnableMongoRepositories
public class LoginServiceApplication implements CommandLineRunner {

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(LoginServiceApplication.class, args);
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.deepak.login")).paths(PathSelectors.any()).build();
	}

	@Override
	public void run(String... args) {
		// Check if roles already exist in the collection
		if (roleRepo.count() == 0) {
			// Insert roles from EnumRole into the collection
			for (EnumRole enumRole : EnumRole.values()) {
				roleRepo.save(new Roles(enumRole));
			}
		}
	}
}
