package com.synapsis.shop.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 5, max = 255)
    private String password;
}
