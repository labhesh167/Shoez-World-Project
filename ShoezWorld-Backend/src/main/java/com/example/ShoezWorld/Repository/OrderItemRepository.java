package com.example.ShoezWorld.Repository;

import com.example.ShoezWorld.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // changed to return productId instead of productName
    @Query("SELECT oi.productId FROM OrderItem oi GROUP BY oi.productId ORDER BY SUM(oi.quantity) DESC")
    List<Long> findMostSoldProductId();
}
