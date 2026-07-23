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
import com.aadiandjava.entity.Product;
import com.aadiandjava.entity.User;
import com.aadiandjava.repository.CartRepository;
import com.aadiandjava.repository.OrderRepository;
import com.aadiandjava.repository.ProductRepository;
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

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Cart> cartItems = cartRepository.findByUserId(user.getId());
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cannot place order: Cart is empty.");
        }

        double totalAmount = 0;
        for (Cart item : cartItems) {
            Product product = item.getProduct();
            
            // 🌟 STOCK VALIDATION & DEDUCTION LOGIC
            if (product.getQuantity() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);

            totalAmount += (product.getPrice() * item.getQuantity());
        }

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(Math.round(totalAmount * 100.0) / 100.0);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(request.getShippingAddress());
        order.setPhoneNumber(request.getPhoneNumber());

        Order savedOrder = orderRepository.save(order);

        cartRepository.deleteAll(cartItems);

        return new OrderResponse(savedOrder.getId(), user.getId(), savedOrder.getTotalAmount(), savedOrder.getOrderDate());
    }

    public List<OrderResponse> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(order -> new OrderResponse(order.getId(), order.getUser().getId(), order.getTotalAmount(), order.getOrderDate()))
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> new OrderResponse(order.getId(), order.getUser().getId(), order.getTotalAmount(), order.getOrderDate()))
                .collect(Collectors.toList());
    }
}