package com.synapsis.shop.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartRequest {
    @JsonProperty("product_id")
    @NotNull
    private Integer productId;

    @JsonProperty("quantity")
    @NotNull
    @Min(1)
    private Integer quantity;
}
