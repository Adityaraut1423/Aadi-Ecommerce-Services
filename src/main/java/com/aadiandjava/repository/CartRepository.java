package com.aadiandjava.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aadiandjava.entity.Cart; // 👈 Import from your entity package

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // Navigates user.id inside your Cart entity
    List<Cart> findByUserId(Long userId);

    // Deletes cart entries for a specific user ID
    void deleteByUserId(Long userId);
}