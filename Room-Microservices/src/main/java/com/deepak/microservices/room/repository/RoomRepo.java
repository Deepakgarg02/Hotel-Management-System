package com.deepak.microservices.room.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deepak.microservices.room.model.Room;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long>{

	List<Room> findByRoomAvail(boolean roomAvail);

}
