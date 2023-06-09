package com.deepak.microservices.room.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RoomNotFoundExcption extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RoomNotFoundExcption() {
	}

	public RoomNotFoundExcption(String message) {
		super(message);
	}
}
