package com.deepak.microservices.room.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public void modifyRoomById(long roomId, Room room) {
		// TODO Auto-generated method stub
		roomRepo.save(room);
		
	}

	@Override
	public void deleteRoomById(long roomId) {
		// TODO Auto-generated method stub
		roomRepo.deleteById(roomId);
		
	}

	@Override
	public Optional<Room> getRoomById(long roomId) {
		// TODO Auto-generated method stub
		return roomRepo.findById(roomId);
	}

	@Override
	public List<Room> getRoomAvailable(boolean roomAvail) {
		// TODO Auto-generated method stub
		return roomRepo.findByRoomAvail(roomAvail);
	}
	
	

}
