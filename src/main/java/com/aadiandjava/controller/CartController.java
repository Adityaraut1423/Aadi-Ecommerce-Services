package com.aadiandjava.controller;

import java.util.List;

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
@CrossOrigin(origins = "*") // Critical to prevent browser cross-origin blocking mechanics
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(@RequestBody CartRequest request) {
        return ResponseEntity.ok(cartService.addItemToCart(request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartResponse>> viewCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUser(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeCartItem(@PathVariable Long id) {
        cartService.removeCartItem(id);
        return ResponseEntity.ok("Item reference successfully purged from active table storage state.");
    }
}