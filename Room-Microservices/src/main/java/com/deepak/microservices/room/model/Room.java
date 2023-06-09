package com.deepak.microservices.room.model;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
@Document(collection = "Room-Service")
public class Room {
	
	@Id
 	private String roomId;
	
	@NotNull(message = "Room Type can not be Null")
	private String roomType;
	
	@NotNull(message = "Room Available can not be Null")
	private boolean roomAvail;
	
	@NotNull(message = "Room Price can not be Null")
	@Positive
	private double roomPrice;

}
