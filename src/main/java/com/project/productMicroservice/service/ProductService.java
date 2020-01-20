package com.project.productMicroservice.service;

import com.project.productMicroservice.entity.Product;

public interface ProductService {

    Product addProduct(Product product);
    Product updateProduct(Product product);
}
