package com.aadiandjava.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aadiandjava.entity.Product;
import com.aadiandjava.repository.ProductRepository;
import com.aadiandjava.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repo;

    @Override
    public Product save(Product product) {
        return repo.save(product);
    }

    @Override
    public List<Product> getAll() {
        return repo.findAll();
    }

    @Override
    public Product getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Product update(Long id, Product product) {
        Product p = repo.findById(id).orElse(null);
        if (p != null) {
            p.setName(product.getName());
            p.setDescription(product.getDescription());
            p.setPrice(product.getPrice());
            p.setQuantity(product.getQuantity());
            p.setImageUrl(product.getImageUrl());
            return repo.save(p);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}