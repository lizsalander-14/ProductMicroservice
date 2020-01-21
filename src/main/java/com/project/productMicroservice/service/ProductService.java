package com.project.productMicroservice.service;

import com.project.productMicroservice.entity.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(Product product);
    Product getProductDetailsById(String productId);
    List<Product> getPopularProducts();
    Iterable<Product> getProductsByCategory();
}
