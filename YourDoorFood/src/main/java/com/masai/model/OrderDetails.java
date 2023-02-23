package com.masai.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int orderId;
	
	private LocalDateTime orderDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Customer customer;
	
	private String orderStatus;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private Map<Item, Integer> itemList;
}
