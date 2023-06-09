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

import com.deepak.microservices.guest.exception.GuestNotFoundException;
import com.deepak.microservices.guest.model.Guest;
import com.deepak.microservices.guest.service.GuestService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/guest")
public class GuestController {

	@Autowired
	private GuestService guestServiceImpl;

	@GetMapping("/get")
	@ApiOperation(value = "Getting Guest Details")
	public List<Guest> getAllGuests() {
		return guestServiceImpl.getAllGuests();
	}

	@PostMapping("/add")
	@ApiOperation(value = "Adding Guest Details")
	public String addGuest(@RequestBody Guest guest) {
		try {
			guestServiceImpl.addGuest(guest);
			return "Guest Added with guestId " + guest.getGuestId();
		} catch (Exception e) {
			return "Use Proper Input";
		}
	}

	@PutMapping("/modify/{guestId}")
	@ApiOperation(value = "Modifying Guest Details By Id")
	public String modifyGuest(@PathVariable String guestId, @RequestBody Guest guest) {
		try {
			guestServiceImpl.modifyGuest(guestId, guest);
			return "Guest Updated with the guestId " + guestId;
		} catch (GuestNotFoundException e) {
			return "Invalid Guest Id";
		}
	}

	@DeleteMapping("/delete/{guestId}")
	@ApiOperation(value = "Deleting Guest Details By Id")
	public String deleteGuest(@PathVariable String guestId) {
		try {
			guestServiceImpl.deleteGuest(guestId);
			return "Guest Deleted with guestId " + guestId;
		} catch (GuestNotFoundException e) {
			return "Invalid Guest Id";
		}
	}

	@GetMapping("/get/{guestId}")
	@ApiOperation(value = "Getting Guest Details By Id")
	public Optional<Guest> getGuestById(@PathVariable String guestId) {
		try {
			return guestServiceImpl.getGuestById(guestId);
		} catch (GuestNotFoundException e) {
			return Optional.empty();
		}
	}

}
