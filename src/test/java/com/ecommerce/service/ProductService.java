package com.ecommerce.service;

import com.ecommerce.dao.OrderProcessorRepositoryImpl;
import com.ecommerce.entity.Product;

public class ProductService {

    OrderProcessorRepositoryImpl repo = new OrderProcessorRepositoryImpl();

    // Including All Columns
    public boolean sampleProduct1(){
        Product product = new Product();
        product.setName("Test Product New");
        product.setPrice(7777);
        product.setDescription("For Testing Purpose");
        product.setStockQuantity(10);

        return repo.createProduct(product);
    }

    // Left Product Name Column
    public boolean sampleProduct2(){
        Product product = new Product();
        product.setPrice(1499);
        product.setDescription("Description");

        return repo.createProduct(product);
    }

}
