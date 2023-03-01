package com.masai.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDTO {
	
	private String currentPassword;
	
	@NotNull(message = "Password can not be null")
	@NotBlank(message = "Password can not be blank")
	@NotEmpty(message = "Password can not be empty")
	@Size(min = 8, max = 15, message = "Password length should be 8 to 15")
	private String newPassword;
	
}
