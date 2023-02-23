package com.masai.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "restaurantGenrator")
    @SequenceGenerator(name = "restaurantGenrator",sequenceName = "resgen",allocationSize = 1,initialValue = 1)
	private Integer restaurantId;
	private String restaurantName;
	private Address address;
	@ManyToMany(cascade=CascadeType.ALL)
	private List<Item> items;
	private String managerName;
	private String contactNumber;
	
}
