package com.project.productMicroservice.dto;

import lombok.Data;

@Data
public class ProductDetailsDto {

    private String merchantId;
    private String productId;
    private int productQuantity;
    private double productPrice;
}
