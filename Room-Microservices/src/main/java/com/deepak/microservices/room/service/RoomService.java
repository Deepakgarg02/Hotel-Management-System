package com.deepak.microservices.room.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deepak.microservices.room.model.Room;

@Service
public interface RoomService {
	
	List<Room> getAllRooms();
	
	void addRoom(Room room);
	
	void modifyRoomById(String roomId, Room room);
	
	void deleteRoomById(String roomId);
	
	Optional<Room> getRoomById(String roomId);
	
	List<Room> getRoomAvailable(boolean roomAvail);

}
