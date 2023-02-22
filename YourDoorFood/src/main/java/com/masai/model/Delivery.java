package com.masai.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;


@Data
@Entity
public class Delivery {

	@Id
	private Integer deliveryId;
	
	@OneToMany
	private List<FoodCart> foodCarts = new ArrayList<>();
	
	
}
