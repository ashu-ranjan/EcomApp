package com.ecommerce.app;

import com.ecommerce.dao.*;
import com.ecommerce.entity.Customer;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.OrderNotFoundException;
import com.ecommerce.exception.ProductNotFoundException;

import java.util.*;

public class EcomApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final OrderProcessorRepositoryImpl repo = new OrderProcessorRepositoryImpl();
    private static Customer loggedInCustomer = null;

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n========= EcomApp Welcomes You =========");
            System.out.println("================= MENU =================");
            System.out.println("\n1. Register Customer");
            System.out.println("2. Login");
            System.out.println("3. Create Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Add to Cart");
            System.out.println("6. View Cart");
            System.out.println("7. Place Order");
            System.out.println("8. View Customer Orders");
            System.out.println("0. Exit");
            System.out.print("\nEnter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> registerCustomer();
                case 2 -> login();
                case 3 -> createProduct();
                case 4 -> deleteProduct();
                case 5 -> addToCart();
                case 6 -> viewCart();
                case 7 -> placeOrder();
                case 8 -> viewCustomerOrders();
                case 0 -> System.out.println("...Exiting application. Goodbye!");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    // METHODS BELOW TO HANDLE MENU

    // Login Existing Customers
    private static void login() {
        System.out.println("\n=== Customer Login ===");
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        loggedInCustomer = repo.loginCustomer(email, password);
        if (loggedInCustomer != null) {
            System.out.println("\nLogin successful! Welcome, " + loggedInCustomer.getName());
        } else {
            System.out.println("\nInvalid credentials. Please register first.");
            registerCustomer();
        }
    }

    // Register Customer
    private static void registerCustomer() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);

        if (repo.createCustomer(customer)) {
            loggedInCustomer = customer;
            System.out.println("\nCustomer registered successfully! Customer ID: " + customer.getCustomerId());
        } else {
            System.out.println("\nFailed to register customer.");
        }
    }

    // Create Product
    private static void createProduct() {
        System.out.print("Enter product name: ");
        String name = sc.nextLine();
        System.out.print("Enter price: ");
        double price = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter description: ");
        String desc = sc.nextLine();
        System.out.print("Enter stock quantity: ");
        int stock = sc.nextInt();
        sc.nextLine();

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(desc);
        product.setStockQuantity(stock);

        if (repo.createProduct(product)) {
            System.out.println("\nProduct created successfully! Product ID: " + product.getProductId());
        } else {
            System.out.println("\nFailed to create product.");
        }
    }

    // Delete Product
    private static void deleteProduct() {
        System.out.print("Enter Product ID to delete: ");
        String pid = sc.nextLine();
        if (repo.deleteProduct(pid)) {
            System.out.println("\nProduct deleted successfully.");
        } else {
            System.out.println("\nFailed to delete product.");
        }
    }

    // Add product to cart
    private static void addToCart() {
        if (loggedInCustomer == null) {
            System.out.println("\nPlease register or login first.");
            return;
        }

        System.out.print("Enter Product ID to add to cart: ");
        String pid = sc.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        try {
            Product selected = repo.getProductByFormattedId(pid);

            if (selected != null && repo.addToCart(loggedInCustomer, selected, quantity)) {
                System.out.println("\nProduct added to cart.");
            } else {
                System.out.println("\nFailed to add product to cart.");
            }
        } catch (ProductNotFoundException e) {
            System.err.println("Enter Valid Product ID!");;
        }
    }

    // View Cart
    private static void viewCart() {
        if (loggedInCustomer == null) {
            System.out.println("\nPlease register or login first.");
            return;
        }
        List<Map.Entry<Product, Integer>> cartItems = repo.getAllFromCart(loggedInCustomer);
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("\n--- Cart Items ---");
            System.out.printf("%-15s %-20s %-10s %-10s\n", "Product ID", "Name", "Price", "Quantity");
            for (Map.Entry<Product, Integer> entry : cartItems) {
                Product p = entry.getKey();
                int qty = entry.getValue();
                System.out.printf("%-15s %-20s %-10.2f %-10d\n", p.getProductId(), p.getName(), p.getPrice(), qty);
            }

        }
    }

    // Place Order
    private static void placeOrder() {
        if (loggedInCustomer == null) {
            System.out.println("\nPlease register or login first.");
            return;
        }

        List<Map.Entry<Product, Integer>> cartItems = repo.getAllFromCart(loggedInCustomer);
        if (cartItems.isEmpty()) {
            System.out.println("\nCart is empty. Cannot place order.");
            return;
        }

        List<Map.Entry<Product, Integer>> orderItems = new ArrayList<>();
        for (Map.Entry<Product, Integer> p : cartItems) {
            int availableQty = p.getValue();
            System.out.print("Enter quantity for " + p.getKey().getName() + " (In Cart: " + availableQty + "): ");
            int qty = sc.nextInt();
            sc.nextLine();

            if (qty > availableQty) {
                System.out.println("Requested quantity exceeds! Please enter up to " + availableQty);
                return;
            }
            orderItems.add(new AbstractMap.SimpleEntry<>(p.getKey(), qty));
        }

        System.out.print("Enter shipping address: ");
        String address = sc.nextLine();

        if (repo.placeOrder(loggedInCustomer, orderItems, address)) {
            System.out.println("\nOrder placed successfully!");
        } else {
            System.out.println("\nFailed to place order.");
        }
    }

    // View Customer's Orders
    private static void viewCustomerOrders() {
        if (loggedInCustomer == null) {
            System.out.println("\nPlease register or login first.");
            return;
        }

        try {
            List<Order> orders = repo.getOrdersByCustomer(loggedInCustomer.getCustomerId());

            if (orders.isEmpty()) {
                System.out.println("\nNo orders found.");
            } else {
                System.out.println("\n--- Customer Orders ---");
                for (Order order : orders) {
                    System.out.println("Order ID   : " + order.getOrderId());
                    System.out.println("Order Date : " + order.getOrderDate());
                    System.out.println("Shipping   : " + order.getShippingAddress());
                    System.out.println("Items:");

                    for (OrderItem item : order.getItems()) {
                        Product p = item.getProduct();
                        int qty = item.getQuantity();
                        System.out.println(" - " + p.getProductId() + " | " + p.getName() + " | Quantity: " + qty);
                    }
                }
            }
        } catch (OrderNotFoundException e) {
            System.err.println("No orders made by Customer!!");
        }
    }

}

