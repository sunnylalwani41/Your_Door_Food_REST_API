package com.masai.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int billId;
	
	private LocalDateTime billDate;
	
	@OneToOne(cascade= CascadeType.ALL)
	private OrderDetails order;
	
	private Integer totalItems;
	
	private Double cost;
	
}
