package com.masai.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode
public class Restaurant {

	  @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userGenerator")
	    @SequenceGenerator(name = "userGenerator",sequenceName = "usergen",allocationSize = 1,initialValue = 1)
		private Integer restaurantId;
		private String restaurantName;

		@Embedded
		private Address address;
		@Email
		private String email;
		
		@JsonIgnore
		@NotNull(message = "Enter vaild password")
		@NotBlank(message = "Enter vaild password")
		@NotEmpty(message = "Enter vaild password")
		@Size(min = 8, max = 15, message = "Password length should be 8 to 15")
		private String password;
		
		@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "restaurant")
		private List<Item> items = new ArrayList<>();
		
		private String managerName;
		
		@Size(min = 10,max = 10,message = "Mobile Number Should Be 10 digit's")
		private String mobileNumber;
		
		@JsonIgnore
		@Embedded
		@ElementCollection
		private Set<Customer> customers= new HashSet<>();
		
		@JsonFormat(pattern = "HH:mm:ss")
		private LocalTime openTime;
		@JsonFormat(pattern = "HH:mm:ss")
		private LocalTime closeTime;
		
		@JsonIgnore
		@Embedded
		@ElementCollection
		private List<Suggestion> suggestions;
}
