package com.aadiandjava.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aadiandjava.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);
}