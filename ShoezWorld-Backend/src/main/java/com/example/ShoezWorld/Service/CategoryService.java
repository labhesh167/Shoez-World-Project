package com.example.ShoezWorld.Service;

import com.example.ShoezWorld.Model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();

    Category getById(Long id);

    Category createCategory(Category category);

    Category updateCategory(Long id, Category category);

    void deleteCategory(Long id);

    
}