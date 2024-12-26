package com.nutmeg.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto extends BaseDto {

	private String name;

	private String userId;

	private List<EmailDto> emails;
	private List<AddressDto> addresses = new ArrayList<>();
}
