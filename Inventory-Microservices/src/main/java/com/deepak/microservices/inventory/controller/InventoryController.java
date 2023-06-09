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

import com.deepak.microservices.inventory.exception.InventoryNotFoundException;
import com.deepak.microservices.inventory.model.Inventory;
import com.deepak.microservices.inventory.service.InventoryService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	@Autowired
	private InventoryService inventoryServiceImpl;

	@GetMapping("/get")
	@ApiOperation(value = "Getting All Inventory Details")
	public List<Inventory> getAllInventory() {
		return inventoryServiceImpl.getAllInventorys();
	}

	@PostMapping("/add")
	@ApiOperation(value = "Adding Inventory Details")
	public String addInventory(@RequestBody Inventory inventory) {
		try {
			inventoryServiceImpl.addInventory(inventory);
			return "Inventory Added with inventoryId " + inventory.getInventoryId();
		} catch (Exception e) {
			return "Use Proper Input";
		}
	}

	@PutMapping("/modify/{inventoryId}")
	@ApiOperation(value = "Modifying Inventory Details By Id")
	public String modifyInventoryById(@PathVariable String inventoryId, @RequestBody Inventory inventory) {
		try {
			inventoryServiceImpl.modifyInventoryById(inventoryId, inventory);
			return "Inventory Updated with inventoryId " + inventoryId;
		} catch (InventoryNotFoundException e) {
			return "Invalid Inventory Id";
		}
	}

	@DeleteMapping("/delete/{inventoryId}")
	@ApiOperation(value = "Deleting Inventory Details By Id")
	public String deleteInventoryById(@PathVariable String inventoryId) {
		try {
			inventoryServiceImpl.deleteInvenotryById(inventoryId);
			return "Inventory Deleted with inventoryId " + inventoryId;
		} catch (InventoryNotFoundException e) {
			return "Invalid Inventory Id";
		}
	}

	@GetMapping("/get/{inventoryId}")
	@ApiOperation(value = "Getting Inventory Details By Id")
	public Optional<Inventory> getInventoryById(@PathVariable String inventoryId) {
		try {
			return inventoryServiceImpl.getInventoryById(inventoryId);
		} catch (InventoryNotFoundException e) {
			return Optional.empty();
		}
	}

}
