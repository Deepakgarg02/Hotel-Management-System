package com.deepak.login.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Signup {

  	@Size(min = 3, max = 20, message = "Username must be in between 3 & 20")
	private String username;

 	@Size(min = 12, max = 30, message = "Email must be in between 12 & 30")
	@Email
	private String email;

 	@Size(min = 6, max = 15, message = "Password must be in between 6 & 15")
	private String password;
	
	private Set<String> roles;

}
