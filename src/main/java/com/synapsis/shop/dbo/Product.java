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
import lombok.Data;

@Entity
@Data
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @Size(min = 5, max = 255)
    @Column(name = "name")
    private String name;

    @JsonProperty("category_id")
    @Column(name = "category_id")
    private Integer categoryId;

    @NotNull
    @Column(name = "price")
    private Integer price;

    @CreationTimestamp
    @JsonProperty("created_at")
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @JsonProperty("updated_at")
    @Column(name = "updated_at")
    private Instant updatedAt;
}
