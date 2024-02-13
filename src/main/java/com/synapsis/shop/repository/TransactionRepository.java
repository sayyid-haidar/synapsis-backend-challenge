package com.synapsis.shop.repository;

import com.synapsis.shop.dbo.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    // You can define custom query methods here if needed
}
