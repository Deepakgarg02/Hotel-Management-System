package com.deepak.microservices.guest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deepak.microservices.guest.model.Guest;
 
@Service
public interface GuestService {

	List<Guest> getAllGuests();

	void addGuest(Guest guest);

	void modifyGuest(String guestId, Guest guest);

	void deleteGuest(String guestId);

	Optional<Guest> getGuestById(String guestId);
}
