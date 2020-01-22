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

import java.util.List;

@RestController
@CrossOrigin("*")
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

            //Passing required details to merchant microservice
            final String uri = "http://10.177.69.78:8080/productdetails/updateProduct";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ProductDetailsDto> entityReq = new HttpEntity<>(productDetailsDto, headers);
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.postForObject(uri,entityReq, String.class,productDetailsDto);
            System.out.println(result);

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

    @PostMapping("/getProductDetailsOfMerchant")
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

    @PostMapping("/listOfProductsByMerchant")
    public ResponseEntity<List<MerchantProductListDto>> getListOfProductsSoldByMerchant(@RequestBody List<MerchantProductListDto> productList){

        for (MerchantProductListDto product:productList) {
            Product product1=productService.getProductDetailsById(product.getProductId());
            product.setProductName(product1.getProductName());
            product.setImageUrl(product1.getImageUrl());
        }

        return new ResponseEntity<List<MerchantProductListDto>>(productList,HttpStatus.CREATED);
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
    public ResponseDto<List<Product>> getCategoryProducts(@RequestBody String categoryId){
        ResponseDto<List<Product>> responseDto=new ResponseDto<>();
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

    @GetMapping("/getProductDetails/{productId}")
    public ResponseDto<ProductDetailsPageDto> getProductDetails(@PathVariable("productId") String productId){
        ProductDetailsPageDto productDetails=new ProductDetailsPageDto();
        productDetails.setProduct(productService.getProductDetailsById(productId));
        ResponseDto<ProductDetailsPageDto> responseDto=new ResponseDto<>();
        try{
            final String uri = "http://10.177.69.78:8080/productdetails/merchantProductsList";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entityReq = new HttpEntity<>(productId, headers);
            RestTemplate restTemplate = new RestTemplate();
            List<MerchantDto> result = restTemplate.postForObject(uri,entityReq, List.class);
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

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseDto<String> deleteProduct(@PathVariable("productId") String productId){
        ResponseDto<String> responseDto=new ResponseDto<>();
        try{
            productService.deleteProduct(productId);
            responseDto.setSuccess(true);
            responseDto.setMessage("Product deleted");
        }
        catch (Exception e){
            responseDto.setSuccess(false);
            responseDto.setMessage("Product not deleted!");
        }
        return responseDto;
    }
}
