package com.nutmeg.user.contact.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserContactResponse {
	@JsonProperty("email")
    private List<String> emails;
	@JsonProperty("userId")
    private String userId;
    @JsonProperty("addresses")
    private List<UserAddress> addresses;
}
