package com.deepak.microservices.inventory.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.deepak.microservices.inventory.exception.InventoryNotFoundException;
import com.deepak.microservices.inventory.model.Inventory;

@Service
public interface InventoryService {

	List<Inventory> getAllInventorys();

	void addInventory(Inventory inventory);

	void modifyInventoryById(String inventoryId, Inventory inventory) throws InventoryNotFoundException;

	void deleteInvenotryById(String inventoryId) throws InventoryNotFoundException;

	Optional<Inventory> getInventoryById(String inventoryId) throws InventoryNotFoundException;

}
