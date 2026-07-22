package com.aadiandjava.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartRequest {

    @JsonProperty("productId")
    private Long productId;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("quantity")
    private Integer quantity;

    // 🌟 DEFAULT NO-ARG CONSTRUCTOR (Required for Jackson JSON Deserialization)
    public CartRequest() {
    }

    // 🌟 ALL-ARGS CONSTRUCTOR (Convenient for testing and manual instantiation)
    public CartRequest(Long productId, Long userId, Integer quantity) {
        this.productId = productId;
        this.userId = userId;
        this.quantity = (quantity != null && quantity > 0) ? quantity : 1;
    }

    // --- GETTERS & SETTERS ---
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    // Fallback setter for "product_id" JSON key variations
    @JsonProperty("product_id")
    public void setProduct_id(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Fallback setter for "user_id" JSON key variations
    @JsonProperty("user_id")
    public void setUser_id(Long userId) {
        this.userId = userId;
    }

    public Integer getQuantity() {
        return (quantity != null && quantity > 0) ? quantity : 1;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = (quantity != null && quantity > 0) ? quantity : 1;
    }

    @Override
    public String toString() {
        return "CartRequest{" +
                "productId=" + productId +
                ", userId=" + userId +
                ", quantity=" + quantity +
                '}';
    }
}