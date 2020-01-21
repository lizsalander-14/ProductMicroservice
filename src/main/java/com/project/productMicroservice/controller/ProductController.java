package com.project.productMicroservice.controller;

import com.project.productMicroservice.dto.*;
import com.project.productMicroservice.entity.Product;
import com.project.productMicroservice.service.CategoryService;
import com.project.productMicroservice.service.ProductService;
import com.project.productMicroservice.service.impl.ProducerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(value="/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProducerService producerService;


    //Merchant flow apis
    @PostMapping(value = "/addProduct")
    public ResponseDto<Product> addProduct(@RequestBody ProductDto productDto){
        ProductDetailsDto productDetailsDto=productDto.getProductDetailsDto();
        productDetailsDto.setProductId(productDto.getProductId());
        productDetailsDto.setProductPrice(productDto.getProductPrice());
        Product product=new Product();
        BeanUtils.copyProperties(productDto,product, String.valueOf(productDetailsDto));
        ResponseDto<Product> responseDto = new ResponseDto<>();
        try{
            Product productCreated=productService.addProduct(product);
            productCreated.setProductRating(5);

            //Passing created object to search microservice
            producerService.produce(product);

//            //Passing required details to merchant microservice
//            final String uri = "http://10.177.69.78:8080/productdetails/update";
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity<ProductDetailsDto> entityReq = new HttpEntity<>(productDetailsDto, headers);
//            RestTemplate restTemplate = new RestTemplate();
//            String result = restTemplate.postForObject(uri,entityReq, String.class,productCreated);
//            System.out.println(result);

            responseDto.setData(productCreated);
            responseDto.setSuccess(true);
        }
        catch (Exception e){
            responseDto.setSuccess(false);
            responseDto.setMessage("Product is not created!!");
            e.printStackTrace();
        }
        return responseDto;
    }

    @GetMapping("/getProductDetailsOfMerchant")
    public ResponseEntity<ProductDetailsOfMerchantDto> getProductDetailsOfMerchant(@RequestBody ProductDetailsDto productDetailsDto){

        ProductDetailsOfMerchantDto productDetailsOfMerchantDto=new ProductDetailsOfMerchantDto();
        productDetailsOfMerchantDto.setProductDetailsDto(productDetailsDto);
        Product product=productService.getProductDetailsById(productDetailsDto.getProductId());
        productDetailsOfMerchantDto.setCategoryId(product.getCategoryId());
        productDetailsOfMerchantDto.setProductName(product.getProductName());
        productDetailsOfMerchantDto.setImageUrl(product.getImageUrl());
        productDetailsOfMerchantDto.setProductAttributes(product.getProductAttributes());
        productDetailsOfMerchantDto.setProductDescription(product.getProductDescription());
        productDetailsOfMerchantDto.setProductRating(product.getProductRating());
        productDetailsOfMerchantDto.setCategoryName(categoryService.getCategoryName(product.getCategoryId()));

        return new ResponseEntity<ProductDetailsOfMerchantDto>(productDetailsOfMerchantDto,HttpStatus.CREATED);
    }

    @GetMapping("/listOfProductsByMerchant")
    public ResponseEntity<Iterable<MerchantProductListDto>> getListOfProductsSoldByMerchant(@RequestBody Iterable<MerchantProductListDto> productList){

        for (MerchantProductListDto product:productList) {
            Product product1=productService.getProductDetailsById(product.getProductId());
            product.setProductName(product1.getProductName());
            product.setImageUrl(product1.getImageUrl());
        }

        return new ResponseEntity<Iterable<MerchantProductListDto>>(productList,HttpStatus.CREATED);
    }
    //merchant flow apis end

    //Customer flow apis
    @GetMapping("/getPopularProducts")
    public ResponseDto<List<Product>> getPopularProducts(){
        ResponseDto<List<Product>> responseDto=new ResponseDto<>();
        try{
            responseDto.setData(productService.getPopularProducts());
            responseDto.setSuccess(true);
        }
        catch (Exception e){
            responseDto.setSuccess(false);
            responseDto.setMessage("Couldn't get popular products");
            e.printStackTrace();
        }
        return responseDto;
    }

    @GetMapping("/getCategoryProducts")
    public ResponseDto<Iterable<Product>> getCategoryProducts(@RequestBody String categoryId){
        ResponseDto<Iterable<Product>> responseDto=new ResponseDto<>();
        try{
            responseDto.setData(productService.getProductsByCategory());
            responseDto.setSuccess(true);
        }
        catch (Exception e){
            responseDto.setSuccess(false);
            responseDto.setMessage("Couldn't get category products");
        }
        return responseDto;
    }

    @GetMapping("/getProductDetails")
    public ResponseDto<ProductDetailsPageDto> getProductDetails(@RequestBody String productId){
        ProductDetailsPageDto productDetails=new ProductDetailsPageDto();
        productDetails.setProduct(productService.getProductDetailsById(productId));
        ResponseDto<ProductDetailsPageDto> responseDto=new ResponseDto<>();
        try{
            final String uri = "http://10.177.69.78:8080/productdetails/update";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entityReq = new HttpEntity<>(productId, headers);
            RestTemplate restTemplate = new RestTemplate();
            Iterable<MerchantDto> result = restTemplate.postForObject(uri,entityReq, Iterable .class,productDetails);
            productDetails.setMerchantList(result);
            responseDto.setData(productDetails);
            responseDto.setSuccess(true);
        }
        catch (Exception e){
            responseDto.setSuccess(false);
            responseDto.setMessage("Couldn't get product details");
        }
        return responseDto;
    }
}
