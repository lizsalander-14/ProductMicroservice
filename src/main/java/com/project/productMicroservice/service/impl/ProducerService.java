package com.project.productMicroservice.service.impl;

import com.project.productMicroservice.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProducerService {


    @Autowired
    KafkaTemplate<String,String> productDtoKafkaTemplate;

    private String TopicName="test";
    public void produce(Product product) throws IOException
    {

        this.productDtoKafkaTemplate.send(TopicName,product.toString());


    }
}
