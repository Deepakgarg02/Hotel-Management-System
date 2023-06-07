package com.deepak.microservices.room.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

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
@Entity
public class Room {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long roomId;
	
	@NotNull(message = "Room Type can not be Null")
	private String roomType;
	
	@NotNull(message = "Room Available can not be Null")
	private boolean roomAvail;
	
	@NotNull(message = "Room Price can not be Null")
	private double roomPrice;

}
