package com.project.productMicroservice.service.impl;

import com.project.productMicroservice.entity.Product;
import com.project.productMicroservice.repository.ProductRepository;
import com.project.productMicroservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductDetailsById(String productId) {
        return productRepository.findById(productId).get();
    }

    @Override
    public List<Product> getPopularProducts() {
        return productRepository.getPopularProducts();
    }

    @Override
    public List<Product> getProductsByCategory() {
        return productRepository.findByCategoryId();
    }

    @Override
    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }
}
