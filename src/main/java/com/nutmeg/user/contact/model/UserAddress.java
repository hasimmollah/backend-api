package com.nutmeg.user.contact.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddress {
    @JsonProperty("firstLine")
    String firstLine;
    @JsonProperty("secondLine")
    String secondLine;
    @JsonProperty("postcode")
    String postcode;
}
