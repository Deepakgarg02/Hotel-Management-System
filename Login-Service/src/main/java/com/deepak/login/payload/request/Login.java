package com.deepak.login.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Login {

	@NotBlank
	private String username;

	@NotBlank
	private String password;

}
