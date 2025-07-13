package com.example.ShoezWorld.DTO;

import java.util.List;

public class PlaceOrderRequest {

    private double totalAmount;
    private String status;
    private DeliveryAddressDTO deliveryAddress;
    private List<OrderItemDTO> items;

    public PlaceOrderRequest() {
    }

    public PlaceOrderRequest(double totalAmount, String status, DeliveryAddressDTO deliveryAddress, List<OrderItemDTO> items) {
        this.totalAmount = totalAmount;
        this.status = status;
        this.deliveryAddress = deliveryAddress;
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public DeliveryAddressDTO getDeliveryAddress() {
        return deliveryAddress;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDeliveryAddress(DeliveryAddressDTO deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}
