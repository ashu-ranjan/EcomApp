package com.ecommerce.entity;

public class Cart {
    private int id; // Database id
    private String cartId; // Format: CT-C0001P0001-01
    private int customerId; // FK
    private int productId; // FK
    private int quantity;

    public Cart() {}

    public Cart(int id, String cartId, int customerId, int productId, int quantity) {
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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

