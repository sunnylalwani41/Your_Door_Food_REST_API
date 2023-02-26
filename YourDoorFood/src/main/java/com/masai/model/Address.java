package com.masai.model;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	
	
	@NotNull(message = "Building Name name is required")
	@Min(value = 0, message = "Invalid House No")
	private Integer HouseNO;
	
	@Size(max = 20,message = "Steet Number should be small")
	private String steetNo;

	@Size(max = 25, message = "area name is to long")
	private String area;
	
	@NotNull(message = "City name is required")
	@Size(max = 30, message = "city name is to long")
	private String city;
	
	@NotNull(message = "State name is required")
	@Size(max = 20, message = "State name should  be less then 20")
	private String state;
	
	private String country;
	
	@NotNull(message = "Pincode is required")
	@Size(min = 6,max = 6,message = "Pincode Should be 6 Digits")
	private String pincode;
	
}
