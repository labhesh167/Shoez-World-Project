package com.example.ShoezWorld.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Long id;
    private UserDTO user;
    private double totalAmount;
    private LocalDateTime orderDate;
    private String status;

    // Order items
    private List<OrderItemDTO> items;

    // New: Delivery address DTO (optional)
    private DeliveryAddressDTO deliveryAddress;

    public OrderDTO() {
    }

    public OrderDTO(Long id, UserDTO user, double totalAmount, LocalDateTime orderDate, String status, List<OrderItemDTO> items, DeliveryAddressDTO deliveryAddress) {
        this.id = id;
        this.user = user;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = status;
        this.items = items;
        this.deliveryAddress = deliveryAddress;
    }

    public Long getId() {
        return id;
    }

    public UserDTO getUser() {
        return user;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public DeliveryAddressDTO getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddressDTO deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
