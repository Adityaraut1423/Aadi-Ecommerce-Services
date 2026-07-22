package com.aadiandjava.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aadiandjava.dto.CartRequest;
import com.aadiandjava.dto.CartResponse;
import com.aadiandjava.entity.Cart;
import com.aadiandjava.entity.Product;
import com.aadiandjava.entity.User;
import com.aadiandjava.repository.CartRepository;
import com.aadiandjava.repository.ProductRepository;
import com.aadiandjava.repository.UserRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CartResponse addItemToCart(CartRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User profile not found."));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product entity not found."));

        List<Cart> existingUserCartItems = cartRepository.findByUserId(request.getUserId());
        Cart cartItem = existingUserCartItems.stream()
                .filter(item -> item.getProduct().getId().equals(request.getProductId()))
                .findFirst()
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        } else {
            cartItem = new Cart();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
        }

        Cart savedItem = cartRepository.save(cartItem);
        return mapToResponse(savedItem);
    }

    public List<CartResponse> getCartByUser(Long userId) {
        return cartRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeCartItem(Long cartItemId) {
        if (!cartRepository.existsById(cartItemId)) {
            throw new RuntimeException("Target cart item reference index not found.");
        }
        cartRepository.deleteById(cartItemId);
    }

//    private CartResponse mapToResponse(Cart cart) {
//        return new CartResponse(
//                cart.getId(),
//                cart.getProduct().getId(),
//                cart.getProduct().getName(),
//                cart.getProduct().getPrice(),
//                cart.getQuantity(),
//                cart.getProduct().getImageUrl()
//        );
//    }
//
    private CartResponse mapToResponse(Cart cart) {
        return new CartResponse(
                cart.getId(),
                cart.getProduct().getId(),
                cart.getProduct().getName(),
                cart.getProduct().getPrice(),
                cart.getQuantity(),
                cart.getProduct().getImageUrl() // 👈 STOP HERE: Double check this method call!
        );
    }
}