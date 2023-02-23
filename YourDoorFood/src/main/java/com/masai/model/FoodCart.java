package com.masai.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FoodCart {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int cartId;
	
	@OneToOne
	private Customer customer;
	
	@OneToMany
	private List<Item> itemList;
	
	
}
