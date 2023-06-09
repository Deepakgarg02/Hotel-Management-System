package com.deepak.microservices.inventory.controller;

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

import com.deepak.microservices.inventory.model.Inventory;
import com.deepak.microservices.inventory.service.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	@Autowired
	private InventoryService inventoryServiceImpl;

	@GetMapping("/get")
	public List<Inventory> getAllInventory() {
		return inventoryServiceImpl.getAllInventorys();
	}

	@PostMapping("/add")
	public String addInventory(@RequestBody Inventory inventory) {
		try {
			inventoryServiceImpl.addInventory(inventory);
			return "Inventory Added with inventoryId " + inventory.getInventoryId();
		} catch (Exception e) {
			return "Use Proper Input";
		}
	}

	@PutMapping("/modify/{inventoryId}")
	public String modifyInventoryById(@PathVariable String inventoryId, @RequestBody Inventory inventory) {
		inventoryServiceImpl.modifyInventoryById(inventoryId, inventory);
		return "Inventory Updated with inventoryId " + inventoryId;
	}

	@DeleteMapping("/delete/{inventoryId}")
	public String deleteInventoryById(@PathVariable String inventoryId) {
		inventoryServiceImpl.deleteInvenotryById(inventoryId);
		return "Inventory Deleted with inventoryId " + inventoryId;
	}

	@GetMapping("/get/{inventoryId}")
	public Optional<Inventory> getInventoryById(@PathVariable String inventoryId) {
		return inventoryServiceImpl.getInventoryById(inventoryId);
	}

}
