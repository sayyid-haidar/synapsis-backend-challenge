package com.synapsis.shop.repository;

import com.synapsis.shop.dbo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // You can define custom query methods here if needed
}
