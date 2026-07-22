package com.aadiandjava.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aadiandjava.dto.CartRequest;
import com.aadiandjava.dto.CartResponse;
import com.aadiandjava.entity.Cart; // 👈 Import from your entity package
import com.aadiandjava.repository.CartRepository;
import com.aadiandjava.service.CartService;

import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<CartResponse> getCartByUser(Long userId) {
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        List<CartResponse> responseList = new ArrayList<>();

        if (cartItems != null) {
            for (Cart item : cartItems) {
                CartResponse response = new CartResponse();
                response.setId(item.getId());
                response.setQuantity(item.getQuantity());

                if (item.getProduct() != null) {
                    response.setProductId(item.getProduct().getId());
                    response.setProductName(item.getProduct().getName());
                    response.setPrice(item.getProduct().getPrice());
                    response.setImageUrl(item.getProduct().getImageUrl());
                }

                responseList.add(response);
            }
        }
        return responseList;
    }

    @Override
    public CartResponse addItemToCart(CartRequest request) {
        // ... Keep your existing logic here ...
        return new CartResponse();
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
}