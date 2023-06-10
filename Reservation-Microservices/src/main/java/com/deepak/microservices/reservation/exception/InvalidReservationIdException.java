package com.deepak.microservices.reservation.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidReservationIdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidReservationIdException() {
	}

	public InvalidReservationIdException(String message) {
		super(message);
	}
}
