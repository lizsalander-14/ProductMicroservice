package com.project.productMicroservice.dto;

import com.project.productMicroservice.entity.Product;
import lombok.Data;

@Data
public class ProductDetailsPageDto {

    private Product product;
    private Iterable<MerchantDto> merchantList;
}
