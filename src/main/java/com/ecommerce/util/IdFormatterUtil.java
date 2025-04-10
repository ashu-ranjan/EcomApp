package com.ecommerce.util;

import java.time.LocalDate;

public class IdFormatterUtil {

    // Pads ID with zeroes (e.g. 1 -> 0001)
    private static String padId(int id) {
        return String.format("%04d", id);
    }

    // Customer: CUST-2025-0001
    public static String generateCustomerId(int id) {
        int year = LocalDate.now().getYear();
        return "CUST-" + year + "-" + padId(id);
    }

    // Product: PROD-2025-0001
    public static String generateProductId(int id) {
        int year = LocalDate.now().getYear();
        return "PROD-" + year + "-" + padId(id);
    }

    // Order: ORD-CT0001-0001
    public static String generateOrderId(int id, int customerId) {
        return "ORD-CT" + padId(customerId) + "-" + padId(id);
    }

    // OrderItem: OITM-ORD0003-01
    public static String generateOrderItemId(int orderId, int itemIndex) {
        return "OITM-ORD" + padId(orderId) + "-" + String.format("%02d", itemIndex);
    }

    // Cart: CT-C0001P0001-01
    public static String generateCartId(int customerId,int productId, int itemIndex) {
        return "CT-C" + padId(customerId) + "P" + padId(productId) + "-" + String.format("%02d", itemIndex);
    }

    // Extract raw ID from formatted string like CUST-2025-0001 -> 1
    public static int extractRawId(String formattedId) {
        try {
            String[] parts = formattedId.split("-");
            return Integer.parseInt(parts[parts.length - 1]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid formatted ID: " + formattedId);
        }
    }

    // Extract order ID from order item: OITM-ORD0003-01 ->1
    public static int extractOrderIdFromOrderItemId(String orderItemId) {
        try {
            String core = orderItemId.split("-")[1]; // ORD0003
            return Integer.parseInt(core.replace("ORD", ""));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid order item ID: " + orderItemId);
        }
    }

    // Extract customer ID from cart ID: CART-CUST0001-01 -> 1
    public static int extractCustomerIdFromCartId(String cartId) {
        try {
            String core = cartId.split("-")[1]; // CUST0001
            return Integer.parseInt(core.replace("CUST", ""));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid cart ID: " + cartId);
        }
    }
}
