package com.deepak.microservices.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.deepak.microservices.room.exception.RoomNotAvailableException;
import com.deepak.microservices.room.exception.RoomNotFoundExcption;
import com.deepak.microservices.room.model.Room;
import com.deepak.microservices.room.repository.RoomRepo;
import com.deepak.microservices.room.service.RoomService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RoomMicroservicesApplicationTests {

	@MockBean
	private RoomRepo roomRepo;

	@Autowired
	private RoomService roomService;

	@Test
    public void getAllRoomsTest() {
	 when(roomRepo.findAll()).thenReturn(Stream
			 .of(new Room("1234567", "Average", true, 15000.0),
			 new Room("1234567", "Bad", false, 5000.0))
			 .collect(Collectors.toList()));	 
	 
	 List<Room> room = roomService.getAllRooms();
	 assertEquals(2, room.size());
 }

	@Test
	public void addRoomTest() {
		Room room = new Room("1234567", "Average", true, 15000.0);
		roomService.addRoom(room);
		verify(roomRepo, times(1)).save(room);
	}

	@Test
	public void deleteRoomByIdTest() throws RoomNotFoundExcption {
		String roomId = "123456789";
		roomService.deleteRoomById(roomId);
		verify(roomRepo, times(1)).deleteById(roomId);
	}

	@Test
	public void getRoomByIdTest() throws RoomNotFoundExcption {
		String roomId = "123456789";
		roomService.getRoomById(roomId);
		verify(roomRepo, times(1)).findById(roomId);
	}

	@Test
	public void modifyRoomByIdTest() throws RoomNotFoundExcption {
		// Create a Room object with updated values
		Room updatedRoom = new Room("1234568", "Updated", true, 15000.0);

		// Mock the existing Room record
		Room existingRoom = new Room("1234568", "Avergae", false, 20000.0);
		Optional<Room> existingRoomOptional = Optional.of(existingRoom);
		when(roomRepo.findById("1234568")).thenReturn(existingRoomOptional);

		// Call the modifyRoom method
		roomService.modifyRoomById("1234568", updatedRoom);

		// Verify that RoomRepo.save() is called with the updated Room
		verify(roomRepo, times(1)).save(existingRoom);

		// Assert that the fields of the existing Room have been updated
		assertEquals("Updated", existingRoom.getRoomType());
		assertEquals(15000.0, existingRoom.getRoomPrice());
	}

	@Test
	public void getRoomAvailableTest() throws RoomNotAvailableException {
		// Mock the list of available rooms
		List<Room> availableRooms = Arrays.asList(new Room("1", "Type 1", true, 100.0),
				new Room("2", "Type 2", true, 150.0), new Room("3", "Type 3", true, 200.0));
		when(roomRepo.findByRoomAvail(true)).thenReturn(availableRooms);

		// Call the getRoomAvailable method
		List<Room> result = roomService.getRoomAvailable(true);

		// Verify the result
		assertEquals(availableRooms.size(), result.size());
		assertEquals(availableRooms, result);
	}

}
