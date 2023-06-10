package com.deepak.microservices.reservation.client;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.deepak.microservices.reservation.model.*;

@FeignClient(name = "Guest-Microservice")
public interface GuestClient {

	@GetMapping("/guest/get/{guestId}")
	public Optional<Guest> getGuestById(@PathVariable String guestId);
	
	@GetMapping("/guest/get")
	public List<Guest> getAllGuests();
}
