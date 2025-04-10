package com.ecommerce.service;

import com.ecommerce.dao.OrderProcessorRepositoryImpl;
import com.ecommerce.entity.Customer;
import com.ecommerce.entity.Product;

import java.util.List;
import java.util.Map;

public class OrderService {

    OrderProcessorRepositoryImpl repo = new OrderProcessorRepositoryImpl();
    public boolean placeOrderSample (Customer customer){
        if (customer == null){
            return false;
        }
        List<Map.Entry<Product, Integer>> cartItems = repo.getAllFromCart(customer);
        if (cartItems == null || cartItems.isEmpty()){
            return false;
        }
        return repo.placeOrder(customer, cartItems,"TEMP");
    }
}
