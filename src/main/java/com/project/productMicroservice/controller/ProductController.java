package com.project.productMicroservice.controller;

import com.project.productMicroservice.dto.ProductDto;
import com.project.productMicroservice.dto.ResponseDto;
import com.project.productMicroservice.entity.Product;
import com.project.productMicroservice.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/addProduct")
    public ResponseDto<Product> addProduct(@RequestBody ProductDto productDto){
        Product product=new Product();
        BeanUtils.copyProperties(productDto,product);
        ResponseDto<Product> responseDto = new ResponseDto<>();
        try{
            Product productCreated=productService.addProduct(product);
            //Add rating to product
            responseDto.setData(productCreated);
            responseDto.setSuccess(true);
            final String uri = "http://172.16.20.103:8080/product/add";
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(uri, String.class,productCreated);
            System.out.println(result);
            //Send pid and mid to merchant microservice
        }catch (Exception e){
            responseDto.setSuccess(false);
            responseDto.setMessage("Product is not created!!");
        }
        return responseDto;
    }

}
