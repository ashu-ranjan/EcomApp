package com.ecommerce.tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        CartServiceTest.class,
        OrderServiceTest.class,
        ExceptionServiceTest.class,
        ProductServiceTest.class
})
public class EcomTestSuite {

}

