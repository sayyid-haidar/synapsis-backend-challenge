package com.synapsis.shop.repository;

import com.synapsis.shop.dbo.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    abstract List<Product> findByCategoryId(Integer categoryId);

    abstract Page<Product> findByCategoryId(Integer categoryId, Pageable pageable);
}
