package com.deepak.microservices.room.model;

import javax.validation.constraints.DecimalMin;
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
    @DecimalMin(value = "1", inclusive = true, message = "Room Price must be greater than or equal to 1")
	private double roomPrice;
	
	public void setRoomId() {
		String randomId = generateRandomId();
		this.roomId = randomId;
	}
	
	private String generateRandomId() {
		StringBuilder sb = new StringBuilder();
		int idLength = 3;
		String characters = "0123456789";
		for (int i = 0; i < idLength; i++) {
			int randomIndex = (int) (Math.random() * characters.length());
			char randomChar = characters.charAt(randomIndex);
			sb.append(randomChar);
		}
		return sb.toString();
	}

}
