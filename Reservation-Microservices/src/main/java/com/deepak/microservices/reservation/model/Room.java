package com.deepak.microservices.reservation.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Room {

	private String roomId;

	private String roomType;

	private boolean roomAvail;

	private double roomPrice;

}
