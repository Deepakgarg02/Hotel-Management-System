package com.deepak.microservices.guest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deepak.microservices.guest.model.Guest;

@Repository
public interface GuestRepo extends JpaRepository<Guest, Long> {

}
