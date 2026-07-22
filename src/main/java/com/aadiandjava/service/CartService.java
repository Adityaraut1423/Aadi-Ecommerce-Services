package com.aadiandjava.service;

import java.util.List;

import com.aadiandjava.dto.CartRequest;
import com.aadiandjava.dto.CartResponse;

public interface CartService {

    List<CartResponse> getCartByUser(Long userId);

    CartResponse addItemToCart(CartRequest request);

    void removeCartItem(Long cartItemId);

    // 🌟 Clears all items from a user's cart after checkout
    void clearCartByUser(Long userId);
}