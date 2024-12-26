package com.nutmeg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto extends BaseDto {
    String firstLine;
    String secondLine;
    String postcode;
    List<UserDto> userDtos = new ArrayList<>();
}
