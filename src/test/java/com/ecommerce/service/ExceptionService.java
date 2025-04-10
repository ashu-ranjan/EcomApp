package com.ecommerce.service;

import com.ecommerce.dao.OrderProcessorRepositoryImpl;
import com.ecommerce.entity.Customer;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.CustomerNotFoundException;
import com.ecommerce.exception.ProductNotFoundException;

public class ExceptionService {

    OrderProcessorRepositoryImpl repo = new OrderProcessorRepositoryImpl();

    public void addToCartSample(Customer customer, Product product, int quantity) {

        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found or is null.");
        }

        if (product == null) {
            throw new ProductNotFoundException("Product not found or is null.");
        }

        boolean add = repo.addToCart(customer, product, quantity);
        if (!add) {
            throw new CustomerNotFoundException("Customer not added yet!");
        }
    }

}
