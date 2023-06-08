package com.deepak.microservices.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deepak.microservices.inventory.model.Inventory;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Long>{

}
