package com.deepak.microservices.guest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.deepak.microservices.guest.exception.GuestNotFoundException;
import com.deepak.microservices.guest.model.Guest;
import com.deepak.microservices.guest.repository.GuestRepo;
import com.deepak.microservices.guest.service.GuestService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class GuestMicroserviceApplicationTests {

	@MockBean
	private GuestRepo guestRepo;

	@Autowired
	private GuestService guestSer;

	@Test
	public void getAllGuestsTest() {
		when(guestRepo.findAll()).thenReturn(Stream
				.of(new Guest("123456789",1111111111,"Deepak","sumit@gmail.com","Male","Kalayat"),
					new Guest("123456788",1111111112,"Aman","dgarg@gmail.com","Male","Kaithal"))
				.collect(Collectors.toList()));
		
		// Call the method under test
		List<Guest> guests = guestSer.getAllGuests();
		
		// Assert the result
		assertEquals(2, guests.size());
	}

	@Test
	public void addGuestTest() {
		Guest guest = new Guest("123456789", 1111111111, "Deepak", "sumit@gmail.com", "Male", "Kalayat");
		guestSer.addGuest(guest);
		verify(guestRepo, times(1)).save(guest);
	}
	
	@Test
	public void deleteGuestTest() throws GuestNotFoundException {
		String guestId = "123456789";
 		guestSer.deleteGuest(guestId);
		verify(guestRepo, times(1)).deleteById(guestId);
	}
	
	@Test 
	public void getGuestByIdTest() throws GuestNotFoundException {
		String guestId = "123456789";
 		guestSer.getGuestById(guestId);
		verify(guestRepo, times(1)).findById(guestId);
	}

	@Test
	public void modifyGuestTest() throws GuestNotFoundException {
	    // Create a guest object with updated values
	    Guest updatedGuest = new Guest("123456789", 1111111111, "Updated Name", "updated_email@gmail.com", "Male", "Updated Address");

	    // Mock the existing guest record
	    Guest existingGuest = new Guest("123456789", 1111111111, "Deepak", "sumit@gmail.com", "Male", "Kalayat");
	    Optional<Guest> existingGuestOptional = Optional.of(existingGuest);
	    when(guestRepo.findById("123456789")).thenReturn(existingGuestOptional);

	    // Call the modifyGuest method
	    guestSer.modifyGuest("123456789", updatedGuest);

	    // Verify that guestRepo.save() is called with the updated guest
	    verify(guestRepo, times(1)).save(existingGuest);

	    // Assert that the fields of the existing guest have been updated
	    assertEquals("Updated Name", existingGuest.getGuestName());
	    assertEquals("updated_email@gmail.com", existingGuest.getGuestEmail());
	    assertEquals("Updated Address", existingGuest.getGuestAddress());
	}

}
