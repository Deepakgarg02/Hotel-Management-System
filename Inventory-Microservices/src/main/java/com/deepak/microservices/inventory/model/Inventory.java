package com.deepak.microservices.inventory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Inventory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long inventoryId;
	
	@NotNull(message = "Name Cann't be Null")
	private String inventoryName;
	
	private String inventoryType;
	
	@Positive
	private int inventoryStock;

}
