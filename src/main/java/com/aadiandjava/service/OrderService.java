package com.aadiandjava.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aadiandjava.dto.OrderRequest;
import com.aadiandjava.dto.OrderResponse;
import com.aadiandjava.entity.Cart;
import com.aadiandjava.entity.Order;
import com.aadiandjava.entity.User;
import com.aadiandjava.repository.CartRepository;
import com.aadiandjava.repository.OrderRepository;
import com.aadiandjava.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
        // 1. Fetch User
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Fetch User's Cart Items
        List<Cart> cartItems = cartRepository.findByUserId(user.getId());
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cannot place order: Cart is empty.");
        }

        // 3. Calculate Total Amount
        double totalAmount = 0;
        for (Cart item : cartItems) {
            // Price * Quantity
            totalAmount += (item.getProduct().getPrice() * item.getQuantity());
        }

        // Add 18% Tax (Matching frontend logic)
        totalAmount = totalAmount + (totalAmount * 0.18);

        // 4. Create and Save the Order
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(Math.round(totalAmount * 100.0) / 100.0); // Round to 2 decimals
        order.setOrderDate(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        // 5. Clear the User's Cart
        cartRepository.deleteAll(cartItems);

        // 6. Return the Response DTO
        return new OrderResponse(savedOrder.getId(), user.getId(), savedOrder.getTotalAmount(), savedOrder.getOrderDate());
    }

    public List<OrderResponse> getOrdersByUser(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        // Map Order entities to OrderResponse DTOs
        return orders.stream()
                .map(order -> new OrderResponse(order.getId(), order.getUser().getId(), order.getTotalAmount(), order.getOrderDate()))
                .collect(Collectors.toList());
    }
 // GET ALL ORDERS FOR ADMIN
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> new OrderResponse(
                        order.getId(),
                        order.getUser().getId(),
                        order.getTotalAmount(),
                        order.getOrderDate()))
                .collect(Collectors.toList());
    }
}