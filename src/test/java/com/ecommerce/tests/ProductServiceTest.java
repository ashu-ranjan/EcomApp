package com.ecommerce.tests;

import com.ecommerce.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    ProductService service;
    static boolean expected;

    @BeforeAll
    public static void beforeAll(){
        expected = true;
    }

    @BeforeEach
    public void beforeEach(){
        service = new ProductService();
    }

    @AfterEach
    public void afterEach(){
        service = null;
    }

    @Test
    public void testSampleProduct1(){ // every column inserted -> pass
        boolean actual = service.sampleProduct1();
        assertEquals(expected, actual);
        System.out.println("Test case passed and creation successful");
    }

    @Test
    public void testSampleProduct2(){ // left one column and asserting false -> pass
        boolean actual = service.sampleProduct2();
        assertFalse(actual);
        System.out.println("Test case passed but creation failed");
    }
}
