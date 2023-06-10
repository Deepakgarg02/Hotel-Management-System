package com.deepak.microservices.reservation.client;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.deepak.microservices.reservation.model.*;

@FeignClient(name = "Room-Microservice")
public interface RoomClient {

	@GetMapping("/room/get/{roomId}")
	public Optional<Room> getRoomById(@PathVariable String roomId);

	@PutMapping("/room/modify/{roomId}")
	public String modifyRoomById(@RequestBody Room room, @PathVariable String roomId);

	@GetMapping("/room/get")
	public List<Room> getAllRooms();
}
