package com.project.productMicroservice.service.impl;

import com.project.productMicroservice.repository.CategoryRepository;
import com.project.productMicroservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

}
