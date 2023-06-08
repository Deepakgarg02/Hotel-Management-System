package com.deepak.microservices.guest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.microservices.guest.model.Guest;
import com.deepak.microservices.guest.service.GuestService;

@RestController
@RequestMapping("/guest")
public class GuestController {

	@Autowired
	private GuestService guestServiceImpl;

	@GetMapping("/get")
	public List<Guest> getAllGuests() {
		return guestServiceImpl.getAllGuests();
	}

	@PostMapping("/add")
	public String addGuest(@RequestBody Guest guest) {
		try {
		guestServiceImpl.addGuest(guest);
		return "Guest Added with guestId " + guest.getGuestId();
		}
		catch(Exception e) {
			return "Use Proper Input";
		}
	}

	@PutMapping("/modify/{guestId}")
	public String modifyGuest(@PathVariable long guestId, @RequestBody Guest guest) {
		guestServiceImpl.modifyGuest(guestId, guest);
		return "Guest Updated with the guestId " + guestId;
	}

	@DeleteMapping("/delete/{guestId}")
	public String deleteGuest(@PathVariable long guestId) {
		guestServiceImpl.deleteGuest(guestId);
		return "Guest Deleted with guestId " + guestId;
	}

	@GetMapping("/get/{guestId}")
	public Optional<Guest> getGuestById(@PathVariable long guestId) {
		return guestServiceImpl.getGuestById(guestId);
	}

}
