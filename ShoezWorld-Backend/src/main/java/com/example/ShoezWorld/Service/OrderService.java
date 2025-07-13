package com.example.ShoezWorld.Service;

import com.example.ShoezWorld.Model.Order;
import com.example.ShoezWorld.Model.OrderItem;
import com.example.ShoezWorld.Model.User;
import com.example.ShoezWorld.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    //[updated] placeOrder to handle order items safely
    public Order placeOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());

        //set order reference for each order item if items exist
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                item.setOrder(order);
            }
        }

        return orderRepository.save(order); //saves both order & items via CascadeType.ALL
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
