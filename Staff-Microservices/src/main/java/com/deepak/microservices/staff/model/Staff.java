package com.deepak.microservices.staff.model;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "Staff-Service")
public class Staff {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String staffId;

	@NotNull(message = "Staff Name cannnot be Null")
	@Size(min = 3, message = "Staff Name should be minimum of 3 digits")
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Staff Name should only contain letters and spaces")
	private String staffName;

	@NotNull(message = "Staff Address cannnot be Null")
	@Size(min = 5, message = "Staff Address should be minimum of 5 digits")
	private String staffAddress;

	private String staffSalary;

	private int staffAge;

	@NotNull(message = "Staff MailId cannnot be Null")
	@Size(min = 12, message = "Staff MailId should be minimum of 12 digits")
	private String staffEmail;

}
