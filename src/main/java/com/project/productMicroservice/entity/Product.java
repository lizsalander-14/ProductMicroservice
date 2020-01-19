package com.project.productMicroservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document
@Getter
@Setter
public class Product {

    @Id
    String productId;
    String categoryId;
    String productName;
    String productImageUrl;
    Map<String,String> productAttributes;
    int productRating;
    String productDescription;

}
