package com.ecommerce.service;

import com.ecommerce.dao.OrderProcessorRepositoryImpl;
import com.ecommerce.entity.Customer;
import com.ecommerce.entity.Product;

public class CartService {
    OrderProcessorRepositoryImpl repo = new OrderProcessorRepositoryImpl();

    public boolean addToCartSample(Customer customer, Product product, int quantity){
        if (customer == null || product == null) {
            return false;
        }
        return repo.addToCart(customer, product, quantity);
    }
}
