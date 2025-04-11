package com.ecommerce.entity;

public class Cart {
    private int id; // Database id
    private String cartId; // Format: CT-C0001P0001-01
    private Customer customerId; // FK
    private Product productId; // FK
    private int quantity;

    public Cart() {}

    public Cart(int id, String cartId, Customer customerId, Product productId, int quantity) {
        this.id = id;
        this.cartId = cartId;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

