package com.project.productMicroservice.service;

import com.project.productMicroservice.entity.Category;

import java.util.List;

public interface CategoryService {

    Category add(Category category);
    List<Category> getCategories();
    String getCategoryName(String categoryId);
}
