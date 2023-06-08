package com.deepak.microservices.guest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepak.microservices.guest.model.Guest;
import com.deepak.microservices.guest.repository.GuestRepo;
 

@Service
public class GuestServiceImpl implements GuestService{
	
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
	public void modifyGuest(long guestId, Guest guest) {
		// TODO Auto-generated method stub
		guestRepo.save(guest);
		
	}

	@Override
	public void deleteGuest(long guestId) {
		// TODO Auto-generated method stub
		guestRepo.deleteById(guestId);
		
	}

	@Override
	public Optional<Guest> getGuestById(long guestId) {
		// TODO Auto-generated method stub
		return guestRepo.findById(guestId);
	}

}
