package com.ecommerce.entity;

public class OrderItem {
    private int id; // Database id
    private String orderItemId; // Format: OITM-ORD0001-01
    private Order order;
    private Product product;
    private int quantity;

    public OrderItem() {}

    public OrderItem(int id, String orderItemId, Order order, Product product, int quantity) {
        this.id = id;
        this.orderItemId = orderItemId;
        this.order = order;
        this.product = product;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

