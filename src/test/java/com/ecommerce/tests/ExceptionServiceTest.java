package com.ecommerce.tests;

import com.ecommerce.dao.OrderProcessorRepositoryImpl;
import com.ecommerce.entity.Customer;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.CustomerNotFoundException;
import com.ecommerce.exception.ProductNotFoundException;
import com.ecommerce.service.ExceptionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExceptionServiceTest {

    OrderProcessorRepositoryImpl repo;
    ExceptionService service;
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
        service = new ExceptionService();
        repo = new OrderProcessorRepositoryImpl();

        customer1 = repo.loginCustomer("shivam@gmail.com","87654321");
        product1 = repo.getProductByFormattedId("PROD-2025-0005");

        customer2 = repo.loginCustomer("shivam@gmail.com","87654328");
        product2 = repo.getProductByFormattedId("PROD-2025-0005");

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
    public void testExceptionThrows1() { // if does not throws exception -> pass
        assertDoesNotThrow(() -> {
            service.addToCartSample(customer1, product1, 1); // both are not null
        });
    }
    @Test
    public void testExceptionThrows2() { // if throws exception -> pass
        assertThrows(CustomerNotFoundException.class, () -> {
            service.addToCartSample(null, product1, 1); // customer is null
        });
    }

    @Test
    public void testExceptionThrows3() { // if throws exception -> pass
        assertThrows(ProductNotFoundException.class, () -> {
            service.addToCartSample(customer1, null, 1); // product is null
        });
    }

    @Test
    public void testExceptionThrows4() { // if throws exception -> pass
        assertThrows(CustomerNotFoundException.class, () -> {
            service.addToCartSample(customer2, product2, 1); // both are null
        });
    }


}

