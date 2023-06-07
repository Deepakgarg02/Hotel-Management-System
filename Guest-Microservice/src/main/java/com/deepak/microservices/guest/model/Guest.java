package com.deepak.microservices.guest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GeneratorType;

//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;

@Entity
public class Guest {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(message = "Guest ID cannot be null")
 	private long guestId;

	@NotNull(message = "Guest Contact cannot be null")
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

	// Constructors
	public Guest() {
	}

	public Guest(long guestId, String guestName, long guestContact, String guestEmail, String guestGender,
			String guestAddress) {
		this.guestId = guestId;
		this.guestName = guestName;
		this.guestContact = guestContact;
		this.guestEmail = guestEmail;
		this.guestGender = guestGender;
		this.guestAddress = guestAddress;
	}

	// Getters and Setters
	public Long getGuestId() {
		return guestId;
	}

	public void setGuestId(Long guestId) {
		this.guestId = guestId;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public Long getGuestContact() {
		return guestContact;
	}

	public void setGuestContact(Long guestContact) {
		this.guestContact = guestContact;
	}

	public String getGuestEmail() {
		return guestEmail;
	}

	public void setGuestEmail(String guestEmail) {
		this.guestEmail = guestEmail;
	}

	public String getGuestGender() {
		return guestGender;
	}

	public void setGuestGender(String guestGender) {
		this.guestGender = guestGender;
	}

	public String getGuestAddress() {
		return guestAddress;
	}

	public void setGuestAddress(String guestAddress) {
		this.guestAddress = guestAddress;
	}

}
