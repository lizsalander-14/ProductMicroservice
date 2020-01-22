package com.project.productMicroservice.dto;

import lombok.Data;

@Data
public class MerchantDto {

    private String merchantId;
    private String merchantName;
    private double productPrice;
}
