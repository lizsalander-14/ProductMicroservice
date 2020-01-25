package com.project.productMicroservice.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ProductDetailsOfMerchantDto {

    private ProductDetailsDto productDetailsDto;
    private String categoryId;
    private String categoryName;
    private String productName;
    private String imageUrl;
    private Map<String ,String> productAttributes;
    private String productDescription;
    private float productRating;
}
