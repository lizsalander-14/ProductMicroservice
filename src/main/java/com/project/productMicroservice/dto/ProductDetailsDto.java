package com.project.productMicroservice.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class ProductDetailsDto {
    String merchantId;
    String productId;
    int productQuantity;
    double productPrice;
}
