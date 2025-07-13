package com.example.ShoezWorld.Controller;

import com.example.ShoezWorld.Model.Order;
import com.example.ShoezWorld.Model.OrderItem;
import com.example.ShoezWorld.Model.User;
import com.example.ShoezWorld.Model.DeliveryAddress;
import com.example.ShoezWorld.DTO.*;
import com.example.ShoezWorld.Service.OrderService;
import com.example.ShoezWorld.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/{username}/place")
    public ResponseEntity<?> placeOrder(@PathVariable String username, @RequestBody PlaceOrderRequest request) {
        System.out.println("Received order for: " + username);
        System.out.println("Payload: " + request);

        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        if (request.getDeliveryAddress() == null) {
            return ResponseEntity.badRequest().body("Delivery address is required");
        }

        if (request.getItems() == null || request.getItems().isEmpty()) {
            return ResponseEntity.badRequest().body("Order must contain at least one item");
        }

        Order order = new Order();
        order.setUser(userOpt.get());
        order.setTotalAmount(request.getTotalAmount());
        order.setStatus(request.getStatus());

        DeliveryAddressDTO addrDto = request.getDeliveryAddress();
        DeliveryAddress address = new DeliveryAddress();
        address.setFullName(addrDto.getFullName());
        address.setAddress(addrDto.getAddress());
        address.setCity(addrDto.getCity());
        address.setPincode(addrDto.getPincode());
        address.setPhoneNumber(addrDto.getPhoneNumber());
        address.setPaymentMethod(addrDto.getPaymentMethod());
        order.setDeliveryAddress(address);

        List<OrderItem> orderItems = request.getItems().stream().map(itemDto -> {
            OrderItem item = new OrderItem();
            item.setProductId(itemDto.getProductId());
            item.setProductName(itemDto.getProductName());
            item.setPrice(itemDto.getPrice());
            item.setQuantity(itemDto.getQuantity());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());
        order.setItems(orderItems);

        Order placedOrder = orderService.placeOrder(order);
        return ResponseEntity.ok(convertToOrderDTO(placedOrder));
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getOrdersByUser(@PathVariable String username) {
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isEmpty())
            return ResponseEntity.badRequest().body("User not found");

        List<Order> orders = orderService.getOrdersByUser(userOpt.get());
        List<OrderDTO> orderDTOs = orders.stream()
                .map(this::convertToOrderDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderDTO> orderDTOs = orders.stream()
                .map(this::convertToOrderDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        Optional<Order> orderOpt = orderService.getOrderById(id);
        if (orderOpt.isEmpty())
            return ResponseEntity.badRequest().body("Order not found");

        return ResponseEntity.ok(convertToOrderDTO(orderOpt.get()));
    }

    // Cancel order endpoint
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
        Optional<Order> orderOpt = orderService.getOrderById(id);
        if (orderOpt.isEmpty())
            return ResponseEntity.status(404).body(Map.of("error", "Order not found"));

        Order order = orderOpt.get();
        if ("Cancelled".equalsIgnoreCase(order.getStatus())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Order already cancelled."));
        }

        order.setStatus("Cancelled");
        orderService.placeOrder(order); // reuse save
        return ResponseEntity.ok(Map.of("message", "Order cancelled successfully"));
    }

    private OrderDTO convertToOrderDTO(Order order) {
        User user = order.getUser();
        UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole());

        List<OrderItemDTO> itemDTOs = order.getItems() != null
                ? order.getItems().stream().map(item -> new OrderItemDTO(
                        item.getProductId(),
                        item.getProductName(),
                        item.getPrice(),
                        item.getQuantity())).collect(Collectors.toList())
                : null;

        DeliveryAddressDTO addressDTO = null;
        if (order.getDeliveryAddress() != null) {
            DeliveryAddress a = order.getDeliveryAddress();
            addressDTO = new DeliveryAddressDTO(
                    a.getFullName(),
                    a.getAddress(),
                    a.getCity(),
                    a.getPincode(),
                    a.getPhoneNumber(),
                    a.getPaymentMethod());
        }

        return new OrderDTO(
                order.getId(),
                userDTO,
                order.getTotalAmount(),
                order.getOrderDate(),
                order.getStatus(),
                itemDTOs,
                addressDTO);
    }
}
