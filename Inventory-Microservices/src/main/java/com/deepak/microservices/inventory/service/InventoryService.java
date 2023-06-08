package com.deepak.microservices.inventory.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.deepak.microservices.inventory.model.Inventory;

@Service
public interface InventoryService {

	List<Inventory> getAllInventorys();

	void addInventory(Inventory inventory);

	void modifyInventoryById(long inventoryId, Inventory inventory);

	void deleteInvenotryById(long inventoryId);

	Optional<Inventory> getInventoryById(long inventoryId);

}
