package com.deepak.microservices.inventory.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "Inventory-Service")
public class Inventory {
	
	@Id
 	private String inventoryId;
	
	@NotNull(message ="Inventory Name can not be null")
	@NotEmpty
 	private String inventoryName;
	
	@NotNull(message ="Inventory Type can not be null")
 	@NotEmpty
 	private String inventoryType;
	 
	@NotBlank
	@Positive
	private int inventoryStock;

}
