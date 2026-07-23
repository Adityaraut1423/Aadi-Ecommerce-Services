package com.aadiandjava.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aadiandjava.dto.CartRequest;
import com.aadiandjava.dto.CartResponse;
import com.aadiandjava.entity.Cart;
import com.aadiandjava.entity.Product;
import com.aadiandjava.entity.User;
import com.aadiandjava.repository.CartRepository;
import com.aadiandjava.repository.ProductRepository;
import com.aadiandjava.repository.UserRepository;
import com.aadiandjava.service.CartService;

import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<CartResponse> getCartByUser(Long userId) {
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        List<CartResponse> responseList = new ArrayList<>();

        if (cartItems != null) {
            for (Cart item : cartItems) {
                responseList.add(mapToResponse(item));
            }
        }
        return responseList;
    }

    @Override
    @Transactional
    public CartResponse addItemToCart(CartRequest request) {
        if (request.getUserId() == null || request.getProductId() == null) {
            throw new RuntimeException("User ID and Product ID must not be null.");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + request.getUserId()));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + request.getProductId()));

        int qtyToAdd = (request.getQuantity() != null && request.getQuantity() > 0) ? request.getQuantity() : 1;

        List<Cart> existingCartItems = cartRepository.findByUserId(user.getId());
        Cart cartItemToSave = null;

        if (existingCartItems != null) {
            for (Cart item : existingCartItems) {
                if (item.getProduct() != null && item.getProduct().getId().equals(product.getId())) {
                    cartItemToSave = item;
                    cartItemToSave.setQuantity(cartItemToSave.getQuantity() + qtyToAdd);
                    break;
                }
            }
        }

        if (cartItemToSave == null) {
            cartItemToSave = new Cart();
            cartItemToSave.setUser(user);
            cartItemToSave.setProduct(product);
            cartItemToSave.setQuantity(qtyToAdd);
        }

        Cart savedItem = cartRepository.save(cartItemToSave);
        return mapToResponse(savedItem);
    }

    @Override
    public void removeCartItem(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    @Override
    @Transactional
    public void clearCartByUser(Long userId) {
        cartRepository.deleteByUserId(userId);
    }

    private CartResponse mapToResponse(Cart item) {
        CartResponse response = new CartResponse();
        response.setId(item.getId());
        response.setQuantity(item.getQuantity());

        if (item.getProduct() != null) {
            response.setProductId(item.getProduct().getId());
            response.setProductName(item.getProduct().getName());
            response.setPrice(item.getProduct().getPrice());
            response.setImageUrl(item.getProduct().getImageUrl());
        }
        return response;
    }
}