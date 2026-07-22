package com.aadiandjava.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aadiandjava.dto.CartRequest;
import com.aadiandjava.dto.CartResponse;
import com.aadiandjava.service.CartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    // 1. Fetch all cart items for a specific user ID
    @GetMapping("/{userId}")
    public ResponseEntity<?> getCartByUser(@PathVariable Long userId) {
        try {
            List<CartResponse> cartItems = cartService.getCartByUser(userId);
            return ResponseEntity.ok(cartItems != null ? cartItems : Collections.emptyList());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch cart: " + ex.getMessage()));
        }
    }

    // 2. Add or update item in cart
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartRequest request) {
        try {
            CartResponse addedItem = cartService.addItemToCart(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedItem);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Could not add item to cart: " + ex.getMessage()));
        }
    }

    // 3. Remove a single item from cart by cartItemId
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long cartItemId) {
        try {
            cartService.removeCartItem(cartItemId);
            return ResponseEntity.ok(Map.of("message", "Item removed from cart successfully."));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while deleting the item."));
        }
    }

    // 4. Clear all cart items for a specific user ID (used after checkout)
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<?> clearCartByUser(@PathVariable Long userId) {
        try {
            cartService.clearCartByUser(userId);
            return ResponseEntity.ok(Map.of("message", "Cart cleared successfully."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to clear cart: " + ex.getMessage()));
        }
    }
}