package com.deepak.microservices.inventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InventoryNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InventoryNotFoundException() {
	}

	public InventoryNotFoundException(String message) {
		super(message);
	}
}
