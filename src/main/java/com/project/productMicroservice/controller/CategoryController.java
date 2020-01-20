package com.project.productMicroservice.controller;

import com.project.productMicroservice.dto.CategoryDto;
import com.project.productMicroservice.dto.ResponseDto;
import com.project.productMicroservice.entity.Category;
import com.project.productMicroservice.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/addCategory")
    public ResponseEntity<String> addCategory(@RequestBody CategoryDto categoryDto){
        Category category =new Category();
        BeanUtils.copyProperties(categoryDto,category);
        Category categoryCreated=categoryService.add(category);
        return new ResponseEntity<String>(String.valueOf(categoryCreated.getCategoryId()),HttpStatus.CREATED);
    }

    @GetMapping("/getCategories")
    public ResponseDto<List> getCategories(){
        ResponseDto<List> responseDto = new ResponseDto<>();
        try {
            List<Category> categoryList = categoryService.getCategories();
            responseDto.setData(categoryList);
            responseDto.setSuccess(true);
        }
        catch (Exception e){
            responseDto.setSuccess(false);
            responseDto.setMessage("Categories not found");
        }
        return responseDto;
    }
}
