package com.deepak.microservices.room.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepak.microservices.room.exception.RoomNotAvailableException;
import com.deepak.microservices.room.exception.RoomNotFoundExcption;
import com.deepak.microservices.room.model.Room;
import com.deepak.microservices.room.repository.RoomRepo;

@Service
public class RoomServiceImpl implements RoomService{
	
	@Autowired
	private RoomRepo roomRepo;

	@Override
	public List<Room> getAllRooms() {
		// TODO Auto-generated method stub
		return roomRepo.findAll();
	}

	@Override
	public void addRoom(Room room) {
		// TODO Auto-generated method stub
		roomRepo.save(room);
		
	}

	@Override
	public void modifyRoomById(String roomId, Room room) throws RoomNotFoundExcption{
	    Optional<Room> existingRoomOptional = roomRepo.findById(roomId);
	    if (existingRoomOptional.isPresent()) {
	        Room existingRoom = existingRoomOptional.get();
	        // Update the fields of the existing room with the new values
	        existingRoom.setRoomPrice(room.getRoomPrice());
	        existingRoom.setRoomType(room.getRoomType());
	        existingRoom.setRoomAvail(room.isRoomAvail());

	        // Save the updated room record
	        roomRepo.save(existingRoom);
	    }
	}

	@Override
	public void deleteRoomById(String roomId) throws RoomNotFoundExcption{
		// TODO Auto-generated method stub
		roomRepo.deleteById(roomId);
		
	}

	@Override
	public Optional<Room> getRoomById(String roomId) throws RoomNotFoundExcption{
		// TODO Auto-generated method stub
		return roomRepo.findById(roomId);
	}

	@Override
	public List<Room> getRoomAvailable(boolean roomAvail) throws RoomNotAvailableException{
		// TODO Auto-generated method stub
		return roomRepo.findByRoomAvail(roomAvail);
	}
	
	

}
