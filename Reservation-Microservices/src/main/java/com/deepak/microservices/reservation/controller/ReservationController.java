package com.deepak.microservices.reservation.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.microservices.reservation.exception.InvalidReservationIdException;
import com.deepak.microservices.reservation.model.Reservation;
import com.deepak.microservices.reservation.service.ReservationService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	@PostMapping("/add")
	@ApiOperation(value = "Adding Reservation details in DB")
	public String addReservation(@RequestBody Reservation reservation) {
		return reservationService.addReservation(reservation);
	}

	@GetMapping("/get")
	@ApiOperation(value = "Getting all Reservation Details From DB")
	public ResponseEntity<List<Reservation>> getAllReservations() {
		return ResponseEntity.ok(reservationService.gettAllReservations());
	}

	@GetMapping("/get/{resId}")
	@ApiOperation(value = "Getting Reservation Details By Id")
	public ResponseEntity<Optional<Reservation>> getReservationById(@PathVariable String resId) {
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
