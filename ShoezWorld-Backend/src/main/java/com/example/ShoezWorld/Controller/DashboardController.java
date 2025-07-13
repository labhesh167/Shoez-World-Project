package com.example.ShoezWorld.Controller;

import com.example.ShoezWorld.Repository.OrderItemRepository;
import com.example.ShoezWorld.Repository.OrderRepository;
import com.example.ShoezWorld.Repository.ProductRepository;
import com.example.ShoezWorld.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

        @Autowired
        private OrderRepository orderRepo;
        @Autowired
        private UserRepository userRepo;
        @Autowired
        private OrderItemRepository orderItemRepo;
        @Autowired
        private ProductRepository productRepo;

        @GetMapping("/stats")
        public Map<String, Object> getStats() {
                // fetch most sold productId
                Long topProductId = orderItemRepo.findMostSoldProductId()
                                .stream().findFirst().orElse(null);

                // fetch product name and image safely
                String topProductName = (topProductId != null)
                                ? productRepo.findNameByProductId(topProductId).orElse("N/A")
                                : "N/A";

                String imageUrl = (topProductId != null)
                                ? productRepo.findImageByProductId(topProductId).orElse(null)
                                : null;

                return Map.of(
                                "totalOrders", orderRepo.count(),
                                "totalUsers", userRepo.count(),
                                "totalRevenue", orderRepo.findTotalRevenue() == null ? 0 : orderRepo.findTotalRevenue(),
                                "mostSoldProduct", topProductName,
                                "topProductImage", imageUrl);
        }
}
