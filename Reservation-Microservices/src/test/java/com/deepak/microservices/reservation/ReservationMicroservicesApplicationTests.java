package com.deepak.microservices.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.deepak.microservices.reservation.client.GuestClient;
import com.deepak.microservices.reservation.client.RoomClient;
import com.deepak.microservices.reservation.exception.InvalidReservationIdException;
import com.deepak.microservices.reservation.model.Guest;
import com.deepak.microservices.reservation.model.Reservation;
import com.deepak.microservices.reservation.model.Room;
import com.deepak.microservices.reservation.repository.ReservationRepo;
import com.deepak.microservices.reservation.service.ReservationService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ReservationMicroservicesApplicationTests {

	@MockBean
	private ReservationRepo reservationRepo;

	@MockBean
	private RoomClient roomClient;

	@MockBean
	private GuestClient guestClient;

	@Autowired
	private ReservationService reservationService;

	@Test
	public void addReservationTest() {
		Reservation reservation = new Reservation("111222333", "111222334", "111222335", "2021-05-10", "2021-05-12", 2,
				6000.0, null);

		Room room = new Room("111222334", "Deluxe", true, 200.0);
		when(roomClient.getRoomById("111222334")).thenReturn(Optional.of(room));

		Guest guest = new Guest("111222335", 1111111111, "Deepak", "sumit@gmail.com", "Male", "Kalayat");
		when(guestClient.getGuestById("111222335")).thenReturn(Optional.of(guest));

		when(reservationRepo.save(reservation)).thenReturn(reservation);

		String result = reservationService.addReservation(reservation);

		String expected = "Room Id 111222334 reserved for the Guest Id Number 111222335";
		assertEquals(expected, result);
	}

	@Test
	public void getReservationByIdTest() throws InvalidReservationIdException {
		Reservation reservation = new Reservation("111222333", "111222334", "111222335", "2021-05-10", "2021-05-12", 2,
				6000.0, null);

		when(reservationRepo.findById("111222333")).thenReturn(Optional.of(reservation));

		Guest guest = new Guest("111222335", 1111111111, "Deepak", "sumit@gmail.com", "Male", "Kalayat");
		when(guestClient.getGuestById("111222335")).thenReturn(Optional.of(guest));

		Optional<Reservation> result = reservationService.getReservationById("111222333");

		assertEquals(reservation, result.get());
		assertEquals(guest, result.get().getGuest());
	}

	@Test
	public void modifyReservationByIdTest() throws InvalidReservationIdException {
		// Create the existing reservation
		Reservation existingReservation = new Reservation("111222333", "roomId1", "111222335", "2021-05-10",
				"2021-05-12", 2, 6000.0, null);

		// Create the modified reservation
		Reservation modifiedReservation = new Reservation("111222333", "roomId1", "111222335", "2021-06-10",
				"2021-06-12", 2, 7000.0, null);

		// Configure the mock repository to return the existing reservation
		Mockito.when(reservationRepo.findById("111222333")).thenReturn(Optional.of(existingReservation));
		Mockito.when(reservationRepo.save(existingReservation)).thenReturn(existingReservation);

		// Perform the modification
		reservationService.modifyReservationById(modifiedReservation, "111222333");

		// Assert the modifications
		assertEquals(modifiedReservation.getCheckInDate(), existingReservation.getCheckInDate());
		assertEquals(modifiedReservation.getCheckOutDate(), existingReservation.getCheckOutDate());
		assertEquals(modifiedReservation.getNumOfGuest(), existingReservation.getNumOfGuest());
		assertEquals(modifiedReservation.getTotalPrice(), existingReservation.getTotalPrice());
	}

	@Test
	public void deleteReservationByIdTest() throws InvalidReservationIdException {
		Reservation reservation = new Reservation("111222333", "111222334", "111222335", "2021-05-10", "2021-05-12", 2,
				6000.0, null);

		Room room = new Room("111222334", "Deluxe", true, 200.0);
		when(roomClient.getRoomById("111222334")).thenReturn(Optional.of(room));

		when(reservationRepo.findById("111222333")).thenReturn(Optional.of(reservation));

		String result = reservationService.deleteReservationById("111222333");

		String expected = "Reservation Deleted with Id 111222333";
		assertEquals(expected, result);
		assertEquals(true, room.isRoomAvail());
	}
}
