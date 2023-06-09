package com.deepak.microservices.room.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deepak.microservices.room.exception.RoomNotAvailableException;
import com.deepak.microservices.room.exception.RoomNotFoundExcption;
import com.deepak.microservices.room.model.Room;

@Service
public interface RoomService {
	
	List<Room> getAllRooms();
	
	void addRoom(Room room);
	
	void modifyRoomById(String roomId, Room room) throws RoomNotFoundExcption;
	
	void deleteRoomById(String roomId) throws RoomNotFoundExcption;
	
	Optional<Room> getRoomById(String roomId) throws RoomNotFoundExcption;
	
	List<Room> getRoomAvailable(boolean roomAvail) throws RoomNotAvailableException;

}
