package com.masai.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

	@NotNull(message = "Mobile Number is required")
	@NotBlank(message = "Enter vaild Mobile Number")
	@Size(min = 10,max = 10,message = "Mobile Number Should Be 10 digit's")
	private String mobileNumber;
	private String password;
}
