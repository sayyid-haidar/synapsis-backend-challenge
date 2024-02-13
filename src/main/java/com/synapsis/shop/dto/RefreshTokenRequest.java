package com.synapsis.shop.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefreshTokenRequest {
    @JsonProperty("refresh_token")
    private String refreshToken;
}
