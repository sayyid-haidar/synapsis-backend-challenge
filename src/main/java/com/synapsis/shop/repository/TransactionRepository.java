package com.synapsis.shop.repository;

import com.synapsis.shop.dbo.Transaction;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Page<Transaction> findByUserId(Integer userId, Pageable pageable);

    Optional<Transaction> findOneByIdAndUserId(Integer id, Integer userId);
}
