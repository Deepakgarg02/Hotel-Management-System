package com.deepak.microservices.inventory.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.deepak.microservices.inventory.model.Inventory;

@Service
public interface InventoryService {

	List<Inventory> getAllInventorys();

	void addInventory(Inventory inventory);

	void modifyInventoryById(String inventoryId, Inventory inventory);

	void deleteInvenotryById(String inventoryId);

	Optional<Inventory> getInventoryById(String inventoryId);

}
