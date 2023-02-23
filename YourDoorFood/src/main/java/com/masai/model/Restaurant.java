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
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Restaurant {

	  @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "restaurantGenrator")
	    @SequenceGenerator(name = "restaurantGenrator",sequenceName = "resgen",allocationSize = 1,initialValue = 1)
		private Integer restaurantId;
		private String restaurantName;
		@OneToOne(cascade = CascadeType.ALL)
		private Address address;
		@Email
		private String email;
		
		@JsonIgnore
		@NotBlank
		@NotEmpty
		@Size(min = 8, max = 15, message = "Password length should be 8 to 15")
		private String password;
		@ManyToMany(cascade=CascadeType.ALL)
		private List<Item> items =new ArrayList<>();
		private String managerName;
		private String contactNumber;
}
