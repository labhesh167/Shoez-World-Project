package com.example.ShoezWorld.Repository;

import com.example.ShoezWorld.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
