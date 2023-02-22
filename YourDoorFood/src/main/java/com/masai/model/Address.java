package com.masai.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private int addressid;
	
	@NotNull
	@Size(max = 20)
	private String buildingName;
	
	@Size(max = 10)
	private String steetNo;
	
	@Size(max = 15, message = "city name is to long")
	private String area;
	
	@NotNull
	@Size(max = 5, message = "city name is to long")
	private String city;
	
	@NotNull
	private String state;
	
	private String country;
	
	@Size(min = 6,max = 6,message = "Pincode Should be 6 Digit's")
	private int pincode;
	
}
