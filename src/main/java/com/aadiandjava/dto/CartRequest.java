package com.aadiandjava.dto;

public class CartRequest {

    private Long productId;
    private Long userId;
    private Integer quantity;

    // 🌟 DEFAULT NO-ARG CONSTRUCTOR (Required for Jackson JSON Deserialization)
    public CartRequest() {
    }

    // 🌟 ALL-ARGS CONSTRUCTOR (Convenient for testing and manual instantiation)
    public CartRequest(Long productId, Long userId, Integer quantity) {
        this.productId = productId;
        this.userId = userId;
        this.quantity = quantity;
    }

    // --- GETTERS & SETTERS ---
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}