package com.masai.model;


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
	
	
	@NotNull(message = "House number is required")
	@Min(value = 1, message = "Invalid House No")
	private Integer HouseNO;
	
	@Size(min = 5, max = 20, message = "street should be of minimum 5 and maximum 20 characters")
	private String steetName;

	@NotNull
	@Size(min = 5, max = 20, message = "area should be of minimum 5 and maximum 20 characters")
	private String area;
	
	@NotNull(message = "City name is required")
	@Size(min = 5, max = 20, message = "city should be of minimum 5 and maximum 20 characters")
	private String city;
	
	@NotNull(message = "State name is required")
	@Size(min = 5, max = 20, message = "state should be of minimum 5 and maximum 20 characters")
	private String state;
	
	private String country;
	
	@NotNull(message = "Pincode is required")
	@Size(min = 6,max = 6,message = "Pincode Should be 6 Digits")
	private String pincode;
	
}
