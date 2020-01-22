package com.project.productMicroservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Category {

    @Id
    private String categoryId;
    private String categoryName;
    private String categoryImageUrl;

}
