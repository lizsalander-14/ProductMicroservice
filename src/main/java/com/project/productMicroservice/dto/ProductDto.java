package com.project.productMicroservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ProductDto {

    private String productId;
    private String categoryId;
    private String productName;
    private String imageUrl;
    private Map<String,String> productAttributes;
    private double productPrice;
    private float productRating;
    private String productDescription;
    private ProductDetailsDto productDetailsDto;
}
