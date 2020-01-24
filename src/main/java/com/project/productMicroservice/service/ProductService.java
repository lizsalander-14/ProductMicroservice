package com.project.productMicroservice.service;

import com.project.productMicroservice.entity.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(Product product);
    Product getProductDetailsById(String productId);
    List<Product> getPopularProducts();
    List<Product> getProductsByCategory(String categoryId);
    void deleteProduct(String productId);
}
