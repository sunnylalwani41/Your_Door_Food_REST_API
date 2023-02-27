package com.masai.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateDTO {
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@NotNull
	private LocalDate startDate;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@NotNull
	private LocalDate endDate;
	
}
