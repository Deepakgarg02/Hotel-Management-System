package com.deepak.microservices.guest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepak.microservices.guest.model.Guest;
import com.deepak.microservices.guest.repository.GuestRepo;

@Service
public class GuestServiceImpl implements GuestService {

	@Autowired
	private GuestRepo guestRepo;

	@Override
	public List<Guest> getAllGuests() {
		// TODO Auto-generated method stub
		return guestRepo.findAll();
	}

	@Override
	public void addGuest(Guest guest) {
		// TODO Auto-generated method stub
		guestRepo.save(guest);

	}

	@Override
	public void modifyGuest(String guestId, Guest guest) {
		Optional<Guest> existingGuestOptional = guestRepo.findById(guestId);
		if (existingGuestOptional.isPresent()) {
			Guest existingGuest = existingGuestOptional.get();
			// Update the fields of the existing guest with the new values
			existingGuest.setGuestName(guest.getGuestName());
			existingGuest.setGuestAddress(guest.getGuestAddress());
			existingGuest.setGuestContact(guest.getGuestContact());
			existingGuest.setGuestEmail(guest.getGuestEmail());
			existingGuest.setGuestGender(guest.getGuestGender());

			// Save the updated guest record
			guestRepo.save(existingGuest);
			;
		}
	}

	@Override
	public void deleteGuest(String guestId) {
		// TODO Auto-generated method stub
		guestRepo.deleteById(guestId);

	}

	@Override
	public Optional<Guest> getGuestById(String guestId) {
		// TODO Auto-generated method stub
		return guestRepo.findById(guestId);
	}

}
