package com.project.productMicroservice.dto;

import com.project.productMicroservice.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductDetailsPageDto {

    private Product product;
    private List<MerchantDto> merchantList;
}
