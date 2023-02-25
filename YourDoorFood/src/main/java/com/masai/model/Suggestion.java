package com.masai.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Suggestion {

	@NotBlank(message = "item name is required")
	@NotNull(message = "item name is required")
	@NotEmpty(message = "item name is required")
	private String itemName;
	
	private String descripton;
}
