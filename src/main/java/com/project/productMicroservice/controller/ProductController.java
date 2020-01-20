package com.project.productMicroservice.controller;

import com.project.productMicroservice.dto.ProductDetailsDto;
import com.project.productMicroservice.dto.ProductDto;
import com.project.productMicroservice.dto.ResponseDto;
import com.project.productMicroservice.entity.Product;
import com.project.productMicroservice.service.ProductService;
import com.project.productMicroservice.service.impl.ProducerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value="/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProducerService producerService;

    @PostMapping(value = "/addProduct")
    //, produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.POST)
    public ResponseDto<Product> addProduct(@RequestBody ProductDto productDto){
        ProductDetailsDto productDetailsDto=productDto.getProductDetailsDto();
        Product product=new Product();
        BeanUtils.copyProperties(productDto,product, String.valueOf(productDetailsDto));
        ResponseDto<Product> responseDto = new ResponseDto<>();
        try{
            Product productCreated=productService.addProduct(product);
            productCreated.setProductRating(5);
            responseDto.setData(productCreated);
            responseDto.setSuccess(true);
            producerService.produce(product);
            final String uri = "http://10.177.69.78:8080/productdetails/update";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ProductDetailsDto> entityReq = new HttpEntity<>(productDetailsDto, headers);
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.postForObject(uri,entityReq, String.class,productCreated);
            System.out.println(result);
            responseDto.setData(productCreated);
            responseDto.setSuccess(true);
        }catch (Exception e){
            responseDto.setSuccess(false);
            responseDto.setMessage("Product is not created!!");
            e.printStackTrace();
        }
        return responseDto;
    }

    @PutMapping(value = "/updateProduct")
    public ResponseDto<Product> updateProduct(@RequestBody ProductDto productDto){
        Product product=new Product();
        BeanUtils.copyProperties(productDto,product);
        ResponseDto<Product> responseDto=new ResponseDto<>();
        try{
            Product productUpdated=productService.updateProduct(product);
            responseDto.setData(productUpdated);
            responseDto.setSuccess(true);
        }
        catch (Exception e){
            responseDto.setSuccess(false);
            responseDto.setMessage("Product is not updated!!");
            e.printStackTrace();
        }
        return responseDto;
    }

}
