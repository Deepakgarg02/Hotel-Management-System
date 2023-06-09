package com.deepak.microservices.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.deepak.microservices.inventory.exception.InventoryNotFoundException;
import com.deepak.microservices.inventory.model.Inventory;
import com.deepak.microservices.inventory.repository.InventoryRepo;
import com.deepak.microservices.inventory.service.InventoryService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class InventoryMicroservicesApplicationTests {

	@MockBean
	private InventoryRepo inventoryRepo;

	@Autowired
	private InventoryService inventoryService;

	@Test
    public void getAllInventoriesTest() {
	 when(inventoryRepo.findAll()).thenReturn(Stream
			 .of(new Inventory("1234567", "Cutlery", "Sppon", 15),
			  new Inventory("1234568", "Cutlery", "Fork", 20))
			 .collect(Collectors.toList()));	 
	 
	 List<Inventory> inventory = inventoryService.getAllInventorys();
	 assertEquals(2, inventory.size());
 }

	@Test
	public void addInventoryTest() {
		Inventory inventory = new Inventory("1234567", "Cutlery", "Sppon", 15);
		inventoryService.addInventory(inventory);
		verify(inventoryRepo, times(1)).save(inventory);
	}

	@Test
	public void deleteInvenotryByIdTest() throws InventoryNotFoundException {
		String inventoryId = "123456789";
		inventoryService.deleteInvenotryById(inventoryId);
		verify(inventoryRepo, times(1)).deleteById(inventoryId);
	}

	@Test
	public void getInventoryByIdTest() throws InventoryNotFoundException {
		String inventoryId = "123456789";
		inventoryService.getInventoryById(inventoryId);
		verify(inventoryRepo, times(1)).findById(inventoryId);
	}

	@Test
	public void modifyInventoryByIdTest() throws InventoryNotFoundException {
		// Create a Inventory object with updated values
		Inventory updatedInventory = new Inventory("1234568", "Updated", "update", 15);

		// Mock the existing Inventory record
		Inventory existingInventory = new Inventory("1234568", "Cutlery", "Fork", 20);
		Optional<Inventory> existingInventoryOptional = Optional.of(existingInventory);
		when(inventoryRepo.findById("1234568")).thenReturn(existingInventoryOptional);

		// Call the modifyInventory method
		inventoryService.modifyInventoryById("1234568", updatedInventory);

		// Verify that inventoryRepo.save() is called with the updated Inventory
		verify(inventoryRepo, times(1)).save(existingInventory);

		// Assert that the fields of the existing Inventory have been updated
		assertEquals("Updated", existingInventory.getInventoryName());
		assertEquals(15, existingInventory.getInventoryStock());
		assertEquals("update", existingInventory.getInventoryType());
	}

}
