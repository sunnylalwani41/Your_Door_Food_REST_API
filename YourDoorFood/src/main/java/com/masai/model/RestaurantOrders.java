package com.masai.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RestaurantOrders {

	@Id
	private Integer OrderId;
	private Integer restaurantId;
	private Double totalCost;
	
	@Embedded
	@ElementCollection(fetch = FetchType.EAGER)
	private List<ItemQuantityDTO> items = new ArrayList<>();
	
}
