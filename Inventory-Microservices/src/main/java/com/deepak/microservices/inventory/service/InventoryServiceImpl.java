package com.deepak.microservices.inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepak.microservices.inventory.model.Inventory;
import com.deepak.microservices.inventory.repository.InventoryRepo;

@Service
public class InventoryServiceImpl implements InventoryService{
	
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
	public void modifyInventoryById(long inventoryId, Inventory inventory) {
		// TODO Auto-generated method stub
		getInventoryById(inventoryId);
		inventoryRepo.save(inventory);
	}

	@Override
	public void deleteInvenotryById(long inventoryId) {
		// TODO Auto-generated method stub
		inventoryRepo.deleteById(inventoryId);
		
	}

	@Override
	public Optional<Inventory> getInventoryById(long inventoryId) {
		// TODO Auto-generated method stub
		return inventoryRepo.findById(inventoryId);
	}

}
