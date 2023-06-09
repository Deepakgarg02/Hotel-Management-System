package com.deepak.microservices.guest.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
@Document(collection = "Guest-Service")
public class Guest {

	@Id
	@NotNull(message = "Guest ID cannot be null")
	private String guestId;

	@NotNull(message = "Guest Contact cannot be null")
	@Positive
	private long guestContact;

	@NotNull(message = "Guest Name cannot be null")
	@Size(min = 3, message = "Guest Name should be a minimum of 3 characters")
	private String guestName;

	@NotNull(message = "Guest Email cannot be null")
	@Size(min = 12, message = "Guest Email should be a minimum of 12 characters")
	private String guestEmail;

	@NotNull(message = "Guest Gender cannot be null")
	@Size(max = 5, message = "Guest Gender should be a maximum of 5 characters")
	private String guestGender;

	@NotNull(message = "Guest Address cannot be null")
	@Size(min = 5, message = "Guest Address should be a minimum of 5 characters")
	private String guestAddress;

}
