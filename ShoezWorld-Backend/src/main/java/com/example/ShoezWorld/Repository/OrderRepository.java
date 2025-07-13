package com.example.ShoezWorld.Repository;

import com.example.ShoezWorld.Model.Order;
import com.example.ShoezWorld.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    // total revenue query
    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    Double findTotalRevenue();
}
