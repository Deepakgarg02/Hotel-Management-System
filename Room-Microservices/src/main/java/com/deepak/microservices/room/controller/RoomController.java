package com.deepak.microservices.room.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.microservices.room.exception.RoomNotAvailableException;
import com.deepak.microservices.room.exception.RoomNotFoundExcption;
import com.deepak.microservices.room.model.Room;
import com.deepak.microservices.room.service.RoomService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/room")
public class RoomController {

	@Autowired
	private RoomService roomServiceImpl;

	@GetMapping("/get")
	@ApiOperation(value = "Getting All Room Details")
	public List<Room> getAllRooms() {
		return roomServiceImpl.getAllRooms();
	}

	@PostMapping("/add")
	@ApiOperation(value = "Adding Room Details")
	public String addRoom(@RequestBody Room room) {
		roomServiceImpl.addRoom(room);
		return "Room Added with roomId " + room.getRoomId();
	}

	@PutMapping("/modify/{roomId}")
	@ApiOperation(value = "Modifying Room Details By Id")
	public String modifyRoomById(@RequestBody Room room, @PathVariable String roomId) {
		try {
		roomServiceImpl.modifyRoomById(roomId, room);
		return "Room Modified with roomId " + roomId;
		}catch(RoomNotFoundExcption e) {
			return "Invalid Room Id";
		}
	}

	@DeleteMapping("/delete/{roomId}")
	@ApiOperation(value = "Deleting Room Details By Id")
	public String deleteRoomById(@PathVariable String roomId) {
		try {
		roomServiceImpl.deleteRoomById(roomId);
		return "Room Deleted with roomId " + roomId;
		}
		catch(RoomNotFoundExcption e) {
			return "Invalid Room Id";
		}
	}

	@GetMapping("/get/{roomId}")
	@ApiOperation(value = "Getting Room Details By Id")
	public Optional<Room> getRoomById(@PathVariable String roomId) {
		try {
		return roomServiceImpl.getRoomById(roomId);
		}
		catch(RoomNotFoundExcption e) {
			return Optional.empty();
		}
	}
	@GetMapping("/search/{roomAvail}")
	@ApiOperation(value = "Checking Room Availability")
	public ResponseEntity<List<Room>> getAvailRoom(@PathVariable boolean roomAvail) {
		try {
		List<Room> room = roomServiceImpl.getRoomAvailable(roomAvail);
		return ResponseEntity.ok(room);
		}
		catch(RoomNotAvailableException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
