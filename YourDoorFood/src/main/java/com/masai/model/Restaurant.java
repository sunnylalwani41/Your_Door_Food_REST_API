package com.masai.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
public class Restaurant {

	  @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "restaurantGenrator")
	    @SequenceGenerator(name = "restaurantGenrator",sequenceName = "resgen",allocationSize = 1,initialValue = 1)
		private Integer restaurantId;
		private String restaurantName;
		@Embedded
		private Address address;
		@JsonIgnore
		private String password;
		@ManyToMany(cascade=CascadeType.ALL)
		private List<Item> items;
		private String managerName;
		private String contactNumber;
}
