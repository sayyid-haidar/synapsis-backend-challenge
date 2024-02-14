package com.synapsis.shop.dbo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartId implements Serializable {

    @NotNull
    @Column(name = "product_id")
    private Integer productId;

    @NotNull
    @Column(name = "user_id")
    private Integer userId;
}
