package com.synapsis.shop.dbo;

import java.io.Serializable;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "carts")
public class Cart implements Serializable {
    @NotNull
    @JsonProperty("product_id")
    private Integer productId;

    @NotNull
    @JsonProperty("user_id")
    private Integer userId;

    @NotNull
    private Integer quantity;

    @CreationTimestamp
    @JsonProperty("create_at")
    private Instant createdAt;

    @UpdateTimestamp
    @JsonProperty("update_at")
    private Instant updatedAt;
}
