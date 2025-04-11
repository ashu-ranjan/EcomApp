package com.ecommerce.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Order {
    private int id; // database id
    private String orderId; // Format: ORD-CT0001-0001
    private Customer customerId;
    private Date orderDate;
    private double totalPrice;
    private String shippingAddress;


    private List<OrderItem> items; // Has-A (One-to-Many)

    public Order() {}

    public Order(int id, String orderId, Customer customerId, Date orderDate, double totalPrice, String shippingAddress) {
        this.id = id;
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.shippingAddress = shippingAddress;
        this.items = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        if (this.items == null){
            this.items = new ArrayList<>();
        }else{
            this.items.clear();
        }
        if (items != null){
            this.items.addAll(items);
        }
    }
}
