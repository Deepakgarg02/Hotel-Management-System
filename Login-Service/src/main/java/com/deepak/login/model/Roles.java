package com.deepak.login.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "roles")
public class Roles {

	@Id
	private String id;

	private EnumRole name;

	public Roles(EnumRole name) {
		super();
		this.name = name;
	}

}
