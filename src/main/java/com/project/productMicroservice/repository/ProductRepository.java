package com.project.productMicroservice.repository;

import com.project.productMicroservice.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {

    @Query("{$sort:{productRating:-1},$limit:4}")
    List<Product> getPopularProducts();

    List<Product> findByCategoryId();
}
