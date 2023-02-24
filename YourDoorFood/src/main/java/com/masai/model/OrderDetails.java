package com.masai.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	private Status orderStatus;
	
//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@ElementCollection(fetch = FetchType.EAGER)
	private Map<Item, Integer> items = new HashMap<Item, Integer>();
}
