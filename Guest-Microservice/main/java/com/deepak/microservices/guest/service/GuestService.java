package com.deepak.microservices.guest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deepak.microservices.guest.exception.GuestNotFoundException;
import com.deepak.microservices.guest.model.Guest;

@Service
public interface GuestService {

	List<Guest> getAllGuests();

	void addGuest(Guest guest);

	void modifyGuest(String guestId, Guest guest) throws GuestNotFoundException;

	void deleteGuest(String guestId) throws GuestNotFoundException;

	Optional<Guest> getGuestById(String guestId) throws GuestNotFoundException;
}
