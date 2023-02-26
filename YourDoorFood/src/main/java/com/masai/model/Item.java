package com.masai.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
public class Item {

	
	 @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "itemGenrator")
	    @SequenceGenerator(name = "itemGenrator",sequenceName = "itemgen",allocationSize = 1,initialValue = 11)
		private Integer itemId;
	 
	 	@NotNull
	 	@NotBlank
	 	@NotEmpty
		private String itemName;
		
	 	
	 	@NotNull
		@ManyToOne(cascade = CascadeType.ALL)
		private Category category;
		
		@NotNull
	 	@NotBlank
	 	@NotEmpty
		private Integer quantity;
		
		@NotNull
	 	@NotBlank
	 	@NotEmpty
		private Double cost;
		
		@ManyToOne(cascade =CascadeType.ALL)
		private Restaurant restaurant;
}
