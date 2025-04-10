package com.ecommerce.tests;

import com.ecommerce.dao.OrderProcessorRepositoryImpl;
import com.ecommerce.entity.Customer;
import com.ecommerce.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {

    OrderProcessorRepositoryImpl repo;
    OrderService service;
    Customer customer1;
    Customer customer2;

    static boolean expected;

    @BeforeAll
    public static void beforeAll() {
        expected = true;
    }

    @BeforeEach
    public void beforeEach() {
        repo = new OrderProcessorRepositoryImpl();
        service = new OrderService();

        customer1 = repo.loginCustomer("shivam@gmail.com", "87654321");
        customer2 = repo.loginCustomer("shiv@gmail.com", "87654321");

    }

    @AfterEach
    public void afterEach() {
        repo = null;
        service = null;
        customer1 = null;
        customer2 = null;
    }

    @Test
    public void testPlaceOrder1() { // if customer have product in cart -> pass
        boolean actual = service.placeOrderSample(customer1);
        assertEquals(expected, actual, "Order should be placed successfully.");
    }

    @Test
    public void testPlaceOrder2() { // if customer is null -> pass
        boolean actual = service.placeOrderSample(customer2);
        assertFalse(actual, "Customer 2 is null and order not placed");
    }
}

