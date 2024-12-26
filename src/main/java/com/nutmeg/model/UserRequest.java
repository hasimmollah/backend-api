package com.nutmeg.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

	@NotNull(message = "Name is mandatory")
	@Size(min = 1, max = 100, message = "Name must be at least 1 characters long and less than 100")
	private String name;

	@NotNull(message = "Name is mandatory")
	@Size(min = 1, max = 100, message = "User Id must be at least 1 characters long and less than 100")
	private String userId;

}
