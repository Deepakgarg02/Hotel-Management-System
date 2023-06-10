package com.deepak.microservices.reservation.model;

import java.util.List;

import lombok.*;
 

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Guest {

	private String guestId;
	
	private long guestContact;
	
	private String guestName;
	
	private String guestEmail;

	private String guestGender;

	private String guestAddress;
	
	private List<Room> room;

}
