package com.project.productMicroservice.controller;

import com.project.productMicroservice.dto.ProductDto;
import com.project.productMicroservice.dto.ResponseDto;
import com.project.productMicroservice.entity.Product;
import com.project.productMicroservice.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/product/addProduct", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.POST)
    public ResponseDto<Product> addProduct(@RequestBody ProductDto productDto){
        Product product=new Product();
        BeanUtils.copyProperties(productDto,product);
        ResponseDto<Product> responseDto = new ResponseDto<>();
        try{
            Product productCreated=productService.addProduct(product);
            productCreated.setProductRating(5);
            final String uri = "http://10.177.69.50:8080/product/add";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ProductDto> entityReq = new HttpEntity<>(productDto, headers);
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.postForObject(uri,entityReq, String.class,productCreated);
            System.out.println(result);
            responseDto.setData(productCreated);
            responseDto.setSuccess(true);
            //Send pid and mid to merchant microservice
        }catch (Exception e){
            responseDto.setSuccess(false);
            responseDto.setMessage("Product is not created!!");
            e.printStackTrace();
        }
        return responseDto;
    }

}
