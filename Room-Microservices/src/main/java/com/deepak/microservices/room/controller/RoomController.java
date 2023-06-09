package com.deepak.microservices.room.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.microservices.room.model.Room;
import com.deepak.microservices.room.service.RoomService;

@RestController
@RequestMapping("/room")
public class RoomController {

	@Autowired
	private RoomService roomServiceImpl;

	@GetMapping("/get")
	public List<Room> getAllRooms() {
		return roomServiceImpl.getAllRooms();
	}

	@PostMapping("/add")
	public String addRoom(@RequestBody Room room) {
		roomServiceImpl.addRoom(room);
		return "Room Added with roomId " + room.getRoomId();
	}

	@PutMapping("/modify/{roomId}")
	public String modifyRoomById(@RequestBody Room room, @PathVariable String roomId) {
		roomServiceImpl.modifyRoomById(roomId, room);
		return "Room Modified with roomId " + roomId;
	}

	@DeleteMapping("/delete/{roomId}")
	public String deleteRoomById(@PathVariable String roomId) {
		roomServiceImpl.deleteRoomById(roomId);
		return "Room Deleted with roomId " + roomId;
	}

	@GetMapping("/get/{roomId}")
	public Optional<Room> getRoomById(@PathVariable String roomId) {
		return roomServiceImpl.getRoomById(roomId);
	}

	@GetMapping("/search/{roomAvail}")
	public List<Room> getAvailRoom(@PathVariable String roomAvail) {
		return roomServiceImpl.getRoomAvailable(roomAvail);
	}

}
