package com.project.productMicroservice.service.impl;

import com.project.productMicroservice.entity.Product;
import com.project.productMicroservice.repository.ProductRepository;
import com.project.productMicroservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        return productRepository.existsById(productId)?productRepository.findById(productId).get():null;
    }

    @Override
    public List<Product> getPopularProducts() {
//        return productRepository.findAll(PageRequest.of(1,5,Sort.by(Sort.Direction.DESC,"productRating"))).toList();
//        return productRepository.getPopularProducts();
       return productRepository.findAll(Sort.by(Sort.Direction.DESC, "productRating"));
    }

    @Override
    public List<Product> getProductsByCategory(String categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }
}
