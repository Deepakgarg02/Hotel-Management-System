package com.deepak.microservices.reservation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deepak.microservices.reservation.exception.InvalidReservationIdException;
import com.deepak.microservices.reservation.model.Reservation;
import com.deepak.microservices.reservation.model.TransactionDetails;

@Service
public interface ReservationService {

	String addReservation(Reservation reservation);

	List<Reservation> gettAllReservations();

	void modifyReservationById(Reservation reservation, String resId) throws InvalidReservationIdException;

	String deleteReservationById(String resId) throws InvalidReservationIdException;

	Optional<Reservation> getReservationById(String resId) throws InvalidReservationIdException;

	TransactionDetails createTransaction(Double amount);

}
