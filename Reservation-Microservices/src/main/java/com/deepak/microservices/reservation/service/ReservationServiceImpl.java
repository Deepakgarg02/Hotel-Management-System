package com.deepak.microservices.reservation.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.deepak.microservices.reservation.client.GuestClient;
import com.deepak.microservices.reservation.client.RoomClient;
import com.deepak.microservices.reservation.exception.InvalidReservationIdException;
import com.deepak.microservices.reservation.model.Guest;
import com.deepak.microservices.reservation.model.Reservation;
import com.deepak.microservices.reservation.model.Room;
import com.deepak.microservices.reservation.model.TransactionDetails;
import com.deepak.microservices.reservation.repository.ReservationRepo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@Service
public class ReservationServiceImpl implements ReservationService {

	private static final String KEY = "rzp_test_4V6htKCRtM3WCi";
	private static final String KEY_SECRET = "HdzLDp1aDpoc1QA0a5mSplN6";
	private static final String CURRENCY = "INR";
	private static final Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

	@Autowired
	private ReservationRepo reservationRepo;

	@Autowired
	private RoomClient roomClient;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private GuestClient guestClient;
	
    public static String calculateDuration(String checkInDate, String checkOutDate) {
        LocalDate startDate = LocalDate.parse(checkInDate);
        LocalDate endDate = LocalDate.parse(checkOutDate);

        long duration = ChronoUnit.DAYS.between(startDate, endDate);

        return String.valueOf(duration);
    }

	@Override
	public String addReservation(Reservation reservation) {
		// TODO Auto-generated method stub
		String randomReservationId = UUID.randomUUID().toString();
		reservation.setReservationId(randomReservationId);
		Optional<Room> r1 = roomClient.getRoomById(reservation.getRoomId());
		Room room = r1.get();

		Optional<Guest> g1 = guestClient.getGuestById(reservation.getGuestId());
		Guest guest = g1.get();
		
		String d1 = calculateDuration(reservation.getCheckInDate(), reservation.getCheckOutDate());
		double days = Double.parseDouble(d1);

		logger.info("Available Room: {}", room.isRoomAvail());
		logger.info("Available Guest: {}", g1.isPresent());
		logger.info("Available Guest: {}", g1.isPresent() && room.isRoomAvail());
		if (room.isRoomAvail() && g1.isPresent()) 
		{
			double roomPrice = room.getRoomPrice();
			double totalPrice = roomPrice * days;
			reservation.setTotalPrice(totalPrice);
			reservationRepo.save(reservation);
			room.setRoomAvail(false);
			roomClient.modifyRoomById(room, reservation.getRoomId());
			return "Room Id " + room.getRoomId() + " reserved for the Guest Id Number " + guest.getGuestId();
		}
		
		else
		{
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
		}

		return reservations;
	}

	@Override
	public void modifyReservationById(Reservation reservation, String resId) throws InvalidReservationIdException {
		// TODO Auto-generated method stub
		Optional<Reservation> res = reservationRepo.findById(resId);
		Optional<Room> r1 = roomClient.getRoomById(reservation.getRoomId());
		Room room = r1.get();
		
		if (res.isPresent()) {
			Reservation reserve = res.get();
			reserve.setCheckInDate(reservation.getCheckInDate());
			reserve.setCheckOutDate(reservation.getCheckOutDate());
			reserve.setNumOfGuest(reservation.getNumOfGuest());

			if(room.isRoomAvail()) {
				
			String existingRoomId = reserve.getRoomId();
		    Optional<Room> r2 = roomClient.getRoomById(existingRoomId);
			Room existingRoom = r2.get();
			existingRoom.setRoomAvail(true);
			roomClient.modifyRoomById(existingRoom, existingRoomId);
			room.setRoomAvail(false);
			
			roomClient.modifyRoomById(room, reservation.getRoomId());
   			reserve.setRoomId(reservation.getRoomId());
			}
			
			String d1 = calculateDuration(reserve.getCheckInDate(), reserve.getCheckOutDate());
			double days = Double.parseDouble(d1);
			double roomPrice = room.getRoomPrice();
			double totalPrice = roomPrice * days;
			reserve.setTotalPrice(totalPrice);
			
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

		reservation.setGuest(guest);

		return reservationRepo.findById(resId);
	}

	@Override
	public TransactionDetails createTransaction(Double amount) {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("amount", (amount*100));
			jsonObject.put("currency", CURRENCY);

			RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);
			Order order = razorpayClient.orders.create(jsonObject);
			logger.info("Order Details: {}", order);

			return prepareTransactionDetails(order);
			// {"amount":100000,"amount_paid":0,"notes":[],"created_at":1686671711,
//				"amount_due":100000,"currency":"INR","receipt":null,
//				"id":"order_M1TGYf2QVnh43Y","entity":"order","offer_id":null,
//				"status":"created","attempts":0}

		} catch (Exception e) {
			logger.info("RazorPay Exception: {}", e.getMessage());
		}
		return null;

	}

	private TransactionDetails prepareTransactionDetails(Order order) {
		String orderId = order.get("id");
		String currency = order.get("currency");
		Integer amount = order.get("amount" );
		String status = order.get("status");

		TransactionDetails transactionDetails = new TransactionDetails(orderId, currency, amount, status);
		return transactionDetails;
	}

}
