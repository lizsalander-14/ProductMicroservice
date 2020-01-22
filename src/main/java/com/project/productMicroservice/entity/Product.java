package com.project.productMicroservice.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document
@Getter
@Setter
@ToString
public class Product {

    @Id
    private String productId;
    private String categoryId;
    private String productName;
    private String imageUrl;
    private Map<String,String> productAttributes;
    private double productPrice;
    private int productRating;
    private String productDescription;

}
