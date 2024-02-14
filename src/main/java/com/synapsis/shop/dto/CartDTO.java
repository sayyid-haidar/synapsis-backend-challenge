package com.synapsis.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDTO {
    @JsonProperty("product_id")
    private Integer productId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("net_price")
    private Integer netPrice;
}
