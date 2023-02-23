package com.masai.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
public class Item {

	
	 @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "itemGenrator")
	    @SequenceGenerator(name = "itemGenrator",sequenceName = "itemgen",allocationSize = 1,initialValue = 11)
		private Integer itemId;
		private String itemName;
		@ManyToOne(cascade = CascadeType.ALL)
		private Category category;
		private Integer quantity;
		private double cost;
		@ManyToMany(cascade =CascadeType.ALL,mappedBy = "items")
		@JsonIgnore
		private List<Restaurant> restaurants =new ArrayList<>(); 
}
