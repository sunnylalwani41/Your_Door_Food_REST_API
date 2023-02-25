package com.masai.model;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode

public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userGenerator")
    @SequenceGenerator(name = "userGenerator",sequenceName = "usergen",allocationSize = 1,initialValue = 1)
	private Integer customerID;
	
	@NotNull(message = "First name is required")
	private String firstName;
	
	private String lastName;
	
	@Max(value = 100,message = "Age Should be less then 100")
	private Integer age;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private FoodCart foodCart;
	
	private String gender;
	
	@NotNull(message = "Mobile Number is required")
	@NotBlank(message = "Enter vaild Mobile Number")
	@Size(min = 10,max = 10,message = "Mobile Number Should Be 10 digits")
	@Column(unique = true)
	private String mobileNumber;
	
	@NotNull(message = "Email is required")
	@Email(message = "Please enter vaild email")
	private String email;
	
	@JsonIgnore
	@NotNull(message = "Password is required")
	@Pattern(regexp = "^[A-Z][0-9][a-z]*")
	@Size(min = 4,max = 8)
	@NotBlank(message = "Password should not be black")
	private String password;
	
	@NotNull(message = "Address is required")
	@Embedded
	private Address address;
	
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	private List<OrderDetails> orders = new ArrayList<>();

}
