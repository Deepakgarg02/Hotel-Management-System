package com.deepak.microservices.reservation.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.deepak.microservices.reservation.client.GuestClient;
import com.deepak.microservices.reservation.client.RoomClient;
import com.deepak.microservices.reservation.exception.InvalidReservationIdException;
import com.deepak.microservices.reservation.model.Guest;
import com.deepak.microservices.reservation.model.Reservation;
import com.deepak.microservices.reservation.model.Room;
import com.deepak.microservices.reservation.repository.ReservationRepo;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationRepo reservationRepo;

	@Autowired
	private RoomClient roomClient;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private GuestClient guestClient;

	@Override
	public String addReservation(Reservation reservation) {
		// TODO Auto-generated method stub
		String randomReservationId = UUID.randomUUID().toString();
		reservation.setReservationId(randomReservationId);
		Optional<Room> r1 = roomClient.getRoomById(reservation.getRoomId());
		Room room = r1.get();

		Optional<Guest> g1 = guestClient.getGuestById(reservation.getGuestId());
		Guest guest = g1.get();

		if (room.isRoomAvail() || g1.isPresent()) {
			reservationRepo.save(reservation);
			room.setRoomAvail(false);
			roomClient.modifyRoomById(room, reservation.getRoomId());
			return "Room Id " + room.getRoomId() + " reserved for the Guest Id Number " + guest.getGuestId();
		} else {
			return "Room is already Booked";
		}
	}

	@Override
	public List<Reservation> gettAllReservations() {
		// TODO Auto-generated method stub
		List<Reservation> reservations = reservationRepo.findAll();

		for (Reservation reservation : reservations) {
			Optional<Guest> g1 = guestClient.getGuestById(reservation.getGuestId());
			Guest guest = g1.get();
			reservation.setGuest(guest);

//	        g1.ifPresent(reservation::setGuest);

			List<Room> room = roomClient.getAllRooms();
			guest.setRoom(room);
		}

		return reservations;
	}

	@Override
	public void modifyReservationById(Reservation reservation, String resId) throws InvalidReservationIdException {
		// TODO Auto-generated method stub
		Optional<Reservation> res = reservationRepo.findById(resId);
		if (res.isPresent()) {
			Reservation reserve = res.get();
			reserve.setCheckInDate(reservation.getCheckInDate());
			reserve.setCheckOutDate(reservation.getCheckOutDate());
			reserve.setNumOfGuest(reservation.getNumOfGuest());
			reserve.setTotalPrice(reservation.getTotalPrice());

			reservationRepo.save(reserve);
		}

	}

	@Override
	public String deleteReservationById(String resId) throws InvalidReservationIdException {
		// TODO Auto-generated method stub
		Optional<Reservation> res1 = reservationRepo.findById(resId);
		Reservation reservation = res1.get();
		Optional<Room> r1 = roomClient.getRoomById(reservation.getRoomId());
		Room room = r1.get();
		room.setRoomAvail(true);
		roomClient.modifyRoomById(room, reservation.getRoomId());
		reservationRepo.deleteById(resId);
		return "Reservation Deleted with Id " + resId;
	}

	@Override
	public Optional<Reservation> getReservationById(String resId) throws InvalidReservationIdException {
		// TODO Auto-generated method stub
		Optional<Reservation> r1 = reservationRepo.findById(resId);
		Reservation reservation = r1.get();

		Optional<Guest> g1 = guestClient.getGuestById(reservation.getGuestId());
		Guest guest = g1.get();

		List<Room> room = roomClient.getAllRooms();

		guest.setRoom(room);
		reservation.setGuest(guest);

		return reservationRepo.findById(resId);
	}

}
