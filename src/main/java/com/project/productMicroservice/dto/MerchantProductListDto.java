package com.project.productMicroservice.dto;

import lombok.Data;

@Data
public class MerchantProductListDto {

    private String productId;
    private String productName;
    private String imageUrl;
    private double productPrice;
    private int productQuantity;

}
