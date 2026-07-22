package com.aadiandjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aadiandjava.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}