package com.synapsis.shop.dbo;

import java.io.Serializable;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "transactions")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @JsonProperty("user_id")
    @Column(name = "user_id")
    private Integer userId;

    @NotNull
    @JsonProperty("product_id")
    @Column(name = "product_id")
    private Integer productId;

    @NotBlank
    @Size(min = 5, max = 255)
    @Size(max = 225)
    @JsonProperty("product_name")
    @Column(name = "product_name")
    private String productName;

    @NotNull
    @JsonProperty("net_price")
    @Column(name = "net_price")
    private Integer netPrice;

    @NotNull
    @Column(name = "quantity")
    private Integer quantity;

    @CreationTimestamp
    @JsonProperty("created_at")
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @JsonProperty("updated_at")
    @Column(name = "updated_at")
    private Instant updatedAt;
}
