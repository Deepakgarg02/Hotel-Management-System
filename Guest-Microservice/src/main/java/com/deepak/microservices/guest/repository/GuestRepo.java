package com.deepak.microservices.guest.repository;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.deepak.microservices.guest.model.Guest;

@Repository
public interface GuestRepo extends MongoRepository<Guest, String> {

}
