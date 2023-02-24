package com.masai.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity


public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userGenerator")
    @SequenceGenerator(name = "userGenerator",sequenceName = "usergen",allocationSize = 1,initialValue = 1)
	private int customerID;
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@Max(value = 100,message = "Age Should be less then 100")
	private int age;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private FoodCart foodCart;
	
	private String gender;
	
	@Size(min = 10,max = 10,message = "Mobile Number Should Be 10 digit's")
	private String mobileNumber;
	
	@Email(message = "Please enter write email")
	private String email;
	
	@JsonIgnore
	@NotNull
	@Pattern(regexp = "")
	@Size(min = 4,max = 8)
	@NotBlank(message = "Password should not be black")
	private String password;
	
	@NotNull
	@Embedded
	private Address address;

}
