package com.microservice.springsecurity.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "user")
public class Users {

	@Id
	private String id;

	private String username;

	private String password;
}
