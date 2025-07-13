package com.example.ShoezWorld.Repository;

import com.example.ShoezWorld.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(Long categoryId);

    // fetch image by productId
    @Query("SELECT p.image FROM Product p WHERE p.id = :id")
    Optional<String> findImageByProductId(@Param("id") Long id);

    // fetch name by productId
    @Query("SELECT p.name FROM Product p WHERE p.id = :id")
    Optional<String> findNameByProductId(@Param("id") Long id);
}
