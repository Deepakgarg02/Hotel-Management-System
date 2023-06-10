package com.deepak.microservices.reservation.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "reservationmicroservice")
public class Reservation {

	@Id
 	private String reservationId;

	@Column
	private String roomId;

	@Column
	private String guestId;

	@Column
	@NotNull(message = "Date cannot be null")
	@Size(min = 10, max = 10, message = "Date should be in format yyyy-mm-dd")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date should be in format yyyy-mm-dd")
	private String checkInDate;

	@Column
	@NotNull(message = "Date cannot be null")
	@Size(min = 10, max = 10, message = "Date should be in format yyyy-mm-dd")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date should be in format yyyy-mm-dd")
	private String checkOutDate;

	@Column
	private int numOfGuest;

	@Column
	@PositiveOrZero
	private double totalPrice;
	
	@Transient
	private Guest guest;

}
