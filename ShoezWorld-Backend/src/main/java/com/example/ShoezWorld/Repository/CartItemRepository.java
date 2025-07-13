package com.example.ShoezWorld.Repository;

import com.example.ShoezWorld.Model.CartItem;
import com.example.ShoezWorld.Model.Product;
import com.example.ShoezWorld.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);

    Optional<CartItem> findByUserAndProduct(User user, Product product);

    void deleteByUserAndProductId(User user, Long productId);

    void deleteByUser(User user);
}
