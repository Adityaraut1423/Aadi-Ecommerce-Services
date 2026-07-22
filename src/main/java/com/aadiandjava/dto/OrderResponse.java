package com.aadiandjava.dto;

import java.time.LocalDateTime;

public class OrderResponse {
    private Long id;
    private Long userId;
    private Double totalAmount;
    private LocalDateTime orderDate;

    public OrderResponse(Long id, Long userId, Double totalAmount, LocalDateTime orderDate) {
        this.id = id;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
}