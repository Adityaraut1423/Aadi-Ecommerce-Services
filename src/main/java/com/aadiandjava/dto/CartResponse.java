package com.aadiandjava.dto;

public class CartResponse {

    private Long id;
    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
    private String imageUrl;

    // 🌟 DEFAULT NO-ARG CONSTRUCTOR (Required for Jackson & Setter Usage)
    public CartResponse() {
    }

    // ALL-ARGS CONSTRUCTOR
    public CartResponse(Long id, Long productId, String productName, Double price, Integer quantity, String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    // --- GETTERS & SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}