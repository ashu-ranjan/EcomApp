package com.ecommerce.entity;

public class Customer {
    private int id; // Database id (auto-increment)
    private String customerId; // Format: CUST-2025-0001
    private String name;
    private String email;
    private String password;

    public Customer() {}

    public Customer(int id, String customerId, String name, String email, String password) {
        this.id = id;
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) { // Validating Name
            throw new IllegalArgumentException("Name cannot be null or empty!");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){ // Validating email id
            this.email = email;
        }
        else
            throw new IllegalArgumentException("Invalid email format!");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.length() < 8) { // Validating Password
            throw new IllegalArgumentException("Password must be at least 8 characters long!");
        }
        this.password = password;
    }
}

