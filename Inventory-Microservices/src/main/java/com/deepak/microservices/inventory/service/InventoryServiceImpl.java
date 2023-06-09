package com.deepak.microservices.inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepak.microservices.inventory.exception.InventoryNotFoundException;
import com.deepak.microservices.inventory.model.Inventory;
import com.deepak.microservices.inventory.repository.InventoryRepo;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryRepo inventoryRepo;

	@Override
	public List<Inventory> getAllInventorys() {
		// TODO Auto-generated method stub
		return inventoryRepo.findAll();
	}

	@Override
	public void addInventory(Inventory inventory) {
		// TODO Auto-generated method stub
		inventoryRepo.save(inventory);

	}

	@Override
	public void modifyInventoryById(String inventoryId, Inventory inventory) throws InventoryNotFoundException {
		Optional<Inventory> existingInventoryOptional = inventoryRepo.findById(inventoryId);
		if (existingInventoryOptional.isPresent()) {
			Inventory existingInventory = existingInventoryOptional.get();
			// Update the fields of the existing inventory with the new values
			existingInventory.setInventoryName(inventory.getInventoryName());
			existingInventory.setInventoryStock(inventory.getInventoryStock());
			existingInventory.setInventoryType(inventory.getInventoryType());

			// Save the updated inventory record
			inventoryRepo.save(existingInventory);
		}
	}

	@Override
	public void deleteInvenotryById(String inventoryId) throws InventoryNotFoundException {
		// TODO Auto-generated method stub
		inventoryRepo.deleteById(inventoryId);

	}

	@Override
	public Optional<Inventory> getInventoryById(String inventoryId) throws InventoryNotFoundException {
		// TODO Auto-generated method stub
		return inventoryRepo.findById(inventoryId);
	}

}
