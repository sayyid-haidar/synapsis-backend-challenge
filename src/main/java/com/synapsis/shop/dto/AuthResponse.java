package com.synapsis.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    @JsonProperty("user_id")
    private Integer userId;

    private String email;

    private String token;

    @JsonProperty("refresh_token")
    private String refreshToken;
}
