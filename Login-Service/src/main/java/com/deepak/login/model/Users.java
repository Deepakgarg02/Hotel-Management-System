package com.deepak.login.model;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "users")
public class Users {

	@Id
	private String id;

	@NotBlank
	@Size(max = 20, message = "Username Can not be More than 20 Words")
	private String username;

	@NotBlank
	@Email
	@Size(max = 50, message = "Email Can not be More than 50 Words")
	private String email;

	@NotBlank
	@Size(max = 50, message = "Password Can me Maximum of 50 words")
	private String password;

	@DBRef
	private Set<Roles> roles;

	public Users(@NotBlank @Size(max = 20, message = "Username Can not be Mpre than 20 Words") String username,
			@NotBlank @Email @Size(max = 50, message = "Email Can not be More than 50 Words") String email,
			@NotBlank @Size(max = 50, message = "Password Can me Maximum of 50 words") String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}

}
