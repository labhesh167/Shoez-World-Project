package com.example.ShoezWorld.DTO;

public class DeliveryAddressDTO {
    private String fullName;
    private String address;
    private String city;
    private String pincode;
    private String phoneNumber;
    private String paymentMethod;

    public DeliveryAddressDTO() {
    }

    public DeliveryAddressDTO(String fullName, String address, String city, String pincode, String phoneNumber, String paymentMethod) {
        this.fullName = fullName;
        this.address = address;
        this.city = city;
        this.pincode = pincode;
        this.phoneNumber = phoneNumber;
        this.paymentMethod = paymentMethod;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPincode() {
        return pincode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
