package com.project.productMicroservice.service.impl;

import com.project.productMicroservice.repository.ProductRepository;
import com.project.productMicroservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
}
