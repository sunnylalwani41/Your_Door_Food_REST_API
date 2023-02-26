package com.masai.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderDetails {
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "ordersGenerator")
	@SequenceGenerator(name= "ordersGenerator", sequenceName = "ordgen", allocationSize = 1, initialValue = 1001)
	private Integer orderId;
	
	private LocalDateTime orderDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Customer customer;
	
	@Enumerated(EnumType.STRING)
	private Status paymentStatus;
	
	private Double totalAmount;

	@ElementCollection(fetch = FetchType.EAGER)
	private Map<Item, Integer> items = new HashMap<>();
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "orderDetails")
	private Bill bill;
}
