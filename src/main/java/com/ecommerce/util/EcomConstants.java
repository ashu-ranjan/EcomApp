package com.ecommerce.util;

public class EcomConstants {

    // Database Connection

    public static final String GET_USER = "db.user";
    public static final String GET_PASSWORD = "db.password";
    public static final String GET_URL = "db.url";
    public static final String GET_DRIVER = "db.driver";

    // SQL Query Constants

    public static final String INSERT_INTO_CUST = "INSERT INTO customers(name, email, password, customer_id) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_CUST_BY_ID = "UPDATE customers SET customer_id = ? WHERE id = ?";
    public static final String SELECT_CUST_BY_EMAIL = "SELECT * FROM customers WHERE email = ? AND password = ?";
    public static final String INSERT_INTO_PROD = "INSERT INTO products(name, price, description, stock_quantity, product_id) VALUES (?, ?, ?, ?, ?)";
    public static final String UPDATE_PROD_BY_ID = "UPDATE products SET product_id = ? WHERE id = ?";
    public static final String DELETE_PROD_BY_ID = "DELETE FROM products WHERE id = ?";
    public static final String DELETE_CUST_BY_ID = "DELETE FROM customers WHERE id = ?";
    public static final String COUNT_FROM_CART = "SELECT COUNT(*) FROM cart WHERE customer_id = ?";
    public static final String INSERT_INTO_CART = "INSERT INTO cart(cart_id, customer_id, product_id, quantity) VALUES (?, ?, ?, ?)";
    public static final String GET_PROD_BY_ID = "SELECT * FROM products WHERE product_id = ?";
    public static final String DELETE_FROM_CART = "DELETE FROM cart WHERE id = ? AND id = ?";
    public static final String GET_ALL_FROM_CART = "SELECT p.*, c.quantity " +
                                                    "FROM products p " +
                                                    "JOIN cart c ON p.id = c.product_id " +
                                                    "WHERE c.customer_id = ?";
    public static final String INSERT_INTO_ORDER = "INSERT INTO orders(customer_id, order_date, total_price, shipping_address) VALUES (?, ?, ?, ?)";
    public static final String INSERT_INTO_ORDER_ITEM = "INSERT INTO order_items(order_id, product_id, quantity, order_item_id) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_ORDER_BY_ID = "UPDATE orders SET order_id = ? WHERE id = ?";
    public static final String UPDATE_STOCK_QTY = "UPDATE products SET stock_quantity = stock_quantity - ? WHERE id = ?";
    public static final String DELETE_FROM_CART_BY_CID = "DELETE FROM cart WHERE customer_id = ?";
    public static final String GET_ORDER_BY_CUST_ID = "SELECT o.order_id, o.order_date, o.shipping_address,p.* " +
                                                        "FROM orders o " +
                                                        "JOIN order_items oi ON o.id = oi.order_id " +
                                                        "JOIN products p ON oi.product_id = p.id " +
                                                        "WHERE o.customer_id = ?";
}


