package com.synapsis.shop.repository;

import com.synapsis.shop.dbo.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    abstract Optional<User> findOneByEmail(String email);
}
