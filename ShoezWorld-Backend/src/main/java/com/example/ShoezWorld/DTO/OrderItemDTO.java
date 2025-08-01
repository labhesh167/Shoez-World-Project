package com.example.ShoezWorld.DTO;

/**
 * DTO for exposing order item details in API responses.
 * Keeps it lightweight and secure.
 */
public class OrderItemDTO {

    private Long productId;
    private String productName;
    private double price;
    private int quantity;

    public OrderItemDTO() {
    }

    public OrderItemDTO(Long productId, String productName, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
