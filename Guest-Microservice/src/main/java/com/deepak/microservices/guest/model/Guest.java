package com.deepak.microservices.guest.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Guest {
	
	private Long guestId;
	private Long guestContact;
	private String guestEmail;
	private String guestName;
	private String guestAddress;
	private String guestGender;


}
