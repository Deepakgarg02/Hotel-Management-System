package com.deepak.microservices.reservation.controller;

import java.util.List;
import java.util.Optional;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import com.deepak.microservices.reservation.exception.InvalidReservationIdException;
import com.deepak.microservices.reservation.model.Reservation;
import com.deepak.microservices.reservation.service.ReservationService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	private Logger logger = LoggerFactory.getLogger(ReservationController.class);

	@PostMapping("/add")
	@ApiOperation(value = "Adding Reservation details in DB")
	public String addReservation(@RequestBody Reservation reservation) {
		return reservationService.addReservation(reservation);
	}

	@GetMapping("/get")
	@ApiOperation(value = "Getting all Reservation Details From DB")
	@RateLimiter(name = "reservationRateLimiter", fallbackMethod = "allReservationsFallback")
	public ResponseEntity<List<Reservation>> getAllReservations() {
		logger.info("Get All Reservations Handler: ReservationController");
		return ResponseEntity.ok(reservationService.gettAllReservations());

	}

	// Creating fallback method for rate limiter - getAllReservations
	public ResponseEntity<List<Reservation>> allReservationsFallback(Exception e) {
		e.printStackTrace();

		Reservation fallbackReservation = Reservation.builder().checkInDate("2021-05-10").checkOutDate("2021-05-20")
				.roomId("111222335").guestId("111222335").reservationId("111222335").numOfGuest(2).totalPrice(0.0)
				.build();

		return ResponseEntity.ok(List.of(fallbackReservation));
	}

	// Providing RateLimiter with FallBack Method
	@GetMapping("/get/{resId}")
	@ApiOperation(value = "Getting Reservation Details By Id")
	@RateLimiter(name = "reservationRateLimiter", fallbackMethod = "guestRoomFallback")
	public ResponseEntity<Optional<Reservation>> getReservationById(@PathVariable String resId) {

		logger.info("Get Single Reservation Handler: ReservationController");
		try {
			Optional<Reservation> reservation = reservationService.getReservationById(resId);
			if (reservation.isPresent()) {
				return ResponseEntity.ok(reservation);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (InvalidReservationIdException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// Creating fallback method for rate limiter
	public ResponseEntity<Optional<Reservation>> guestRoomFallback(Exception e) {
		e.printStackTrace();

		Reservation fallbackReservation = Reservation.builder().checkInDate("2021-05-10").checkOutDate("2021-05-20")
				.roomId("111222335").guestId("111222335").reservationId("111222335").numOfGuest(2).totalPrice(0.0)
				.build();

		return ResponseEntity.ok(Optional.of(fallbackReservation));
	}

	@PutMapping("/modify/{resId}")
	@ApiOperation(value = "Updating the Reservation Details")
	public String modifyReservationById(@PathVariable String resId, @RequestBody Reservation reservation) {
		try {
			reservationService.modifyReservationById(reservation, resId);
			return "Reservation modified for the Reservation Id " + resId;
		} catch (InvalidReservationIdException e) {
			// TODO: handle exception
			return "Invaild Reservation Id";
		}
	}

	@DeleteMapping("/delete/{resId}")
	@ApiOperation(value = "Deleting Reservation details by Id")
	public ResponseEntity<String> deleteReservationById(@PathVariable String resId) {
		try {
			return ResponseEntity.ok(reservationService.deleteReservationById(resId));
		} catch (InvalidReservationIdException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
}
