package com.project.productMicroservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ProductDto {

    String productId;
    String categoryId;
    String productName;
    String productImageUrl;
    Map<String,String> productAttributes;
    int productRating;
    String productDescription;

}
