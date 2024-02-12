package com.synapsis.shop.dbo;

import java.io.Serializable;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "transactions")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @JsonProperty("product_id")
    private Integer productId;

    @NotBlank
    @Size(min = 5)
    @Size(min = 225)
    @JsonProperty("product_name")
    private String productName;

    @NotNull
    @JsonProperty("net_price")
    private Integer netPrice;

    @NotNull
    private Integer quantity;

    @CreationTimestamp
    @JsonProperty("create_at")
    private Instant createdAt;

    @UpdateTimestamp
    @JsonProperty("update_at")
    private Instant updatedAt;
}
