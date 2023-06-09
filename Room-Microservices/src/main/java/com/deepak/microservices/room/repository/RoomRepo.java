package com.deepak.microservices.room.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deepak.microservices.room.model.Room;

@Repository
public interface RoomRepo extends MongoRepository<Room, String>{

	List<Room> findByRoomAvail(boolean roomAvail);

}
