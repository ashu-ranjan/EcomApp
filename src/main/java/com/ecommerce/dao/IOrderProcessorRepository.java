package com.ecommerce.dao;

import com.ecommerce.entity.Customer;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.Product;
import java.util.List;
import java.util.Map;

public interface IOrderProcessorRepository {

    Customer loginCustomer(String email, String password);

    boolean createProduct(Product product);
    boolean deleteProduct(String productId);

    boolean createCustomer(Customer customer);
    boolean deleteCustomer(String customerId);

    boolean addToCart(Customer customer, Product product, int quantity);

    Product getProductByFormattedId(String formattedId);

    boolean removeFromCart(Customer customer, Product product);

    List<Map.Entry<Product, Integer>> getAllFromCart(Customer customer);

    boolean placeOrder(Customer customer, List<Map.Entry<Product, Integer>> productsWithQuantity, String shippingAddress);
    List<Order> getOrdersByCustomer(String customerId);
}

