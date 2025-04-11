package com.ecommerce.tests;

import com.ecommerce.dao.OrderProcessorRepositoryImpl;
import com.ecommerce.entity.Customer;
import com.ecommerce.entity.Product;
import com.ecommerce.service.CartService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CartServiceTest {

    OrderProcessorRepositoryImpl repo;
    CartService service;
    Customer customer1;
    Product product1;
    Customer customer2;
    Product product2;

    static boolean expected;

    @BeforeAll
    public static void beforeAll(){
        expected = true;
    }

    @BeforeEach
    public void beforeEach(){
        service = new CartService();
        repo = new OrderProcessorRepositoryImpl();

        customer1 = repo.loginCustomer("anjali@gmail.com","anjali12");
        product1 = repo.getProductByFormattedId("PROD-2025-0010");

        customer2 = repo.loginCustomer("shivam@gmail.com","87654328");
        product2 = repo.getProductByFormattedId("PROD-2025-0004");

    }

    @AfterEach
    public void afterEach(){
        repo = null;
        service = null;
        customer1 = null;
        product1 = null;
        customer2 = null;
        product2 = null;
    }

    @Test
    public void testAddToCart1() { //Testing for Valid Customer and product
        assertTrue(service.addToCartSample(customer1, product1, 1));
    }

    @Test
    public void testAddToCart2() { // Testing for Invalid Customer and Valid Product
        assertFalse(service.addToCartSample(null, product1, 1), "Should fail for null customer");
    }

    @Test
    public void testAddToCart3() { // Testing for Valid Customer and invalid Product
        assertFalse(service.addToCartSample(customer1, null, 1), "Should fail for null product");
    }
    @Test
    public void testAddToCart4() { // Testing for Invalid Customer and Product
        assertFalse(service.addToCartSample(customer2, product2, 1));
    }



}

