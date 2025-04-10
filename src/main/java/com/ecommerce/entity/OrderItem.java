package com.ecommerce.entity;

public class OrderItem {
    private int id; // Database id
    private String orderItemId; // Format: OITM-ORD0001-01
    private int orderId;
    private int productId;
    private int quantity;

    public OrderItem() {}

    public OrderItem(int id, String orderItemId, int orderId, int productId, int quantity) {
        this.id = id;
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

