package com.synapsis.shop.repository;

import com.synapsis.shop.dbo.Cart;
import com.synapsis.shop.dbo.CartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, CartId> {
    // You can define custom query methods here if needed
}
