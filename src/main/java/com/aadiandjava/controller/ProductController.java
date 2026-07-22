package com.aadiandjava.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aadiandjava.entity.Product;
import com.aadiandjava.service.ProductService;

@RestController
@RequestMapping("/api/products")  // 👈 THIS IS CRITICAL: Maps all methods in this controller to /api/products
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping("/favicon.ico")
    public void ignoreFavicon() {}

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return service.save(product);
    }

    @GetMapping
    public List<Product> getProducts() {
        return service.getAll();
    }

    @GetMapping("/{id:\\d+}")
    public Product getProduct(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id:\\d+}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return service.update(id, product);
    }

    @DeleteMapping("/{id:\\d+}")
    public void deleteProduct(@PathVariable Long id) {
        service.delete(id);
    }
}