package com.masai.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class OrderDetails {
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "ordersGenerator")
	@SequenceGenerator(name= "ordersGenerator", sequenceName = "ordgen", allocationSize = 1, initialValue = 1001)
	private int orderId;
	
	private LocalDateTime orderDate;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Customer customer;
	
	@Enumerated(EnumType.STRING)
	private Status paymentStatus;
	
	private Double totalAmount;
	
//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@ElementCollection(fetch = FetchType.EAGER)
	private Map<Item, Integer> items = new HashMap<>();
}
