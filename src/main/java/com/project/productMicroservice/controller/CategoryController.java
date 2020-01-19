package com.project.productMicroservice.controller;

import com.project.productMicroservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
}
