package com.deepak.microservices.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deepak.microservices.reservation.model.Reservation;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, String> {

}
