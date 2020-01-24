package com.project.productMicroservice.dto;

import lombok.Data;

@Data
public class CartPageDto {
    private String customerId;
    private String merchantId;
    private String productId;
    private String productName;
    private String imageUrl;
    private int quantityBrought;
    private double productPrice;
}
