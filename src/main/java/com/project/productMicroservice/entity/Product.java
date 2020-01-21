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
    String productId;
    String categoryId;
    String productName;
    String imageUrl;
    Map<String,String> productAttributes;
    double productPrice;
    int productRating;
    String productDescription;

}
