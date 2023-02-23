package com.masai.model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	@Size(max = 20,message = "Building Name should be small")
	private String buildingName;
	
	@Size(max = 10,message = "Steet Number should be small")
	private String steetNo;
	
	@Size(max = 15, message = "area name is to long")
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
