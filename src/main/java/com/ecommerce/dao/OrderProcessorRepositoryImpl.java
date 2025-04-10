package com.ecommerce.dao;

import com.ecommerce.entity.*;
import com.ecommerce.exception.*;
import com.ecommerce.util.*;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderProcessorRepositoryImpl implements IOrderProcessorRepository {

    private static final Logger logger = Logger.getLogger(OrderProcessorRepositoryImpl.class.getName());

    private Connection conn;
    public OrderProcessorRepositoryImpl() {
        this.conn = DbConnectionUtil.getDbConnection();
    }

    // Creating Customer
    @Override
    public boolean createCustomer(Customer customer) {

        try {
            PreparedStatement pstmt = conn.prepareStatement(EcomConstants.INSERT_INTO_CUST, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getPassword());
            pstmt.setString(4, "TEMP"); // Formatted customer id not known yet
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    String formattedId = IdFormatterUtil.generateCustomerId(id);

                    PreparedStatement updateStmt = conn.prepareStatement(EcomConstants.UPDATE_CUST_BY_ID);
                    updateStmt.setString(1, formattedId);
                    updateStmt.setInt(2, id);
                    updateStmt.executeUpdate();

                    customer.setId(id);
                    customer.setCustomerId(formattedId);
                }
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error creating customer", e);
        }
        return false;
    }

    @Override
    public Customer loginCustomer(String email, String password) {

        try {
             PreparedStatement stmt = conn.prepareStatement(EcomConstants.SELECT_CUST_BY_EMAIL);

            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Customer c = new Customer();
                    c.setId(rs.getInt("id"));
                    c.setCustomerId(rs.getString("customer_id"));
                    c.setName(rs.getString("name"));
                    c.setEmail(rs.getString("email"));
                    c.setPassword(rs.getString("password"));
                    return c;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error during customer login", e);
        }
        return null;
    }


    // Creating Product
    @Override
    public boolean createProduct(Product product) {

        try {
            PreparedStatement pstmt = conn.prepareStatement(EcomConstants.INSERT_INTO_PROD, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setString(3, product.getDescription());
            pstmt.setInt(4, product.getStockQuantity());
            pstmt.setString(5, "TEMP");
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    String formattedId = IdFormatterUtil.generateProductId(id);

                    //String updateSql = "UPDATE products SET product_id = ? WHERE id = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(EcomConstants.UPDATE_PROD_BY_ID);
                    updateStmt.setString(1, formattedId);
                    updateStmt.setInt(2, id);
                    updateStmt.executeUpdate();

                    product.setId(id);
                    product.setProductId(formattedId);
                }
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error creating product", e);
        }
        return false;
    }

    @Override
    public boolean deleteProduct(String formattedProductId) {
        int id = IdFormatterUtil.extractRawId(formattedProductId);

        try {
            PreparedStatement pstmt = conn.prepareStatement(EcomConstants.DELETE_PROD_BY_ID);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting product", e);
        }
        return false;
    }

    @Override
    public boolean deleteCustomer(String formattedCustomerId) {
        int id = IdFormatterUtil.extractRawId(formattedCustomerId);

        try {
            PreparedStatement pstmt = conn.prepareStatement(EcomConstants.DELETE_CUST_BY_ID);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting customer", e);
        }
        return false;
    }

    @Override
    public boolean addToCart(Customer customer, Product product, int quantity) {
        try {
            if (product == null || product.getStockQuantity() < quantity) {
                throw new ProductNotFoundException("Product not found or insufficient stock."); // EXCEPTION
            }

            PreparedStatement countStmt = conn.prepareStatement(EcomConstants.COUNT_FROM_CART);
            countStmt.setInt(1, customer.getId());
            ResultSet rs = countStmt.executeQuery();
            int itemIndex = 1;
            if (rs.next()) {
                itemIndex = rs.getInt(1) + 1;
            }

            String cartId = IdFormatterUtil.generateCartId(customer.getId(), product.getId(), itemIndex);

            PreparedStatement pstmt = conn.prepareStatement(EcomConstants.INSERT_INTO_CART);
            pstmt.setString(1, cartId);
            pstmt.setInt(2, customer.getId()); // change done
            pstmt.setInt(3, product.getId());
            pstmt.setInt(4, quantity);

            boolean success = pstmt.executeUpdate() > 0;
            if (success) {
                logger.info("Product added to cart: " + cartId + " | Customer: "
                        + IdFormatterUtil.generateCustomerId(customer.getId()) + " | Product: "
                        + IdFormatterUtil.generateProductId(product.getId()));
            }
            return success;

        } catch (SQLException | ProductNotFoundException e) {
            logger.log(Level.SEVERE, "Error adding to cart", e);
        }
        return false;
    }

    @Override
    public Product getProductByFormattedId(String formattedId) {

        try{
             PreparedStatement ps = conn.prepareStatement(EcomConstants.GET_PROD_BY_ID);
            ps.setString(1, formattedId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setProductId(rs.getString("product_id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setStockQuantity(rs.getInt("stock_quantity"));
                return product;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching product by formatted ID", e);
        }
        return null;
    }


    @Override
    public boolean removeFromCart(Customer customer, Product product) {

        try {
            PreparedStatement pstmt = conn.prepareStatement(EcomConstants.DELETE_FROM_CART);
            pstmt.setInt(1, customer.getId());
            pstmt.setInt(2, product.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error removing from cart", e);
        }
        return false;
    }

    @Override
    public List<Map.Entry<Product, Integer>> getAllFromCart(Customer customer) {
        List<Map.Entry<Product, Integer>> productsWithQty = new ArrayList<>();

        try {
            PreparedStatement pstmt = conn.prepareStatement(EcomConstants.GET_ALL_FROM_CART);
            pstmt.setInt(1, customer.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = extractProductFromResultSet(rs);
                int quantity = rs.getInt("quantity");
                productsWithQty.add(new AbstractMap.SimpleEntry<>(product, quantity));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error getting cart products", e);
        }
        return productsWithQty;
    }


    @Override
    public boolean placeOrder(Customer customer, List<Map.Entry<Product, Integer>> items, String shippingAddress) {

        try {
            conn.setAutoCommit(false);

            double total = 0.0;
            for (Map.Entry<Product, Integer> entry : items) {
                if (entry.getKey().getStockQuantity() < entry.getValue()) {
                    throw new InsufficientStockException("Not enough stock for product: " + entry.getKey().getName());
                }
                total += entry.getKey().getPrice() * entry.getValue();
            }

            // Insert order without order_id (will update after getting auto-generated ID)
            PreparedStatement orderStmt = conn.prepareStatement(EcomConstants.INSERT_INTO_ORDER, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, customer.getId());
            orderStmt.setDate(2, Date.valueOf(LocalDate.now()));
            orderStmt.setDouble(3, total);
            orderStmt.setString(4, shippingAddress);
            orderStmt.executeUpdate();

            ResultSet rs = orderStmt.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            // Generate formatted order ID and update /// ? changed by me
            String formattedOrderId = IdFormatterUtil.generateOrderId(orderId, customer.getId());

            PreparedStatement updateOrder = conn.prepareStatement(EcomConstants.UPDATE_ORDER_BY_ID);
            updateOrder.setString(1, formattedOrderId);
            updateOrder.setInt(2, orderId);
            updateOrder.executeUpdate();

            // Insert order items
            int index = 1;
            for (Map.Entry<Product, Integer> entry : items) {
                String formattedOrderItemId = IdFormatterUtil.generateOrderItemId(orderId, index);
                PreparedStatement itemStmt = conn.prepareStatement(EcomConstants.INSERT_INTO_ORDER_ITEM);
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, entry.getKey().getId());
                itemStmt.setInt(3, entry.getValue());
                itemStmt.setString(4, formattedOrderItemId);
                itemStmt.executeUpdate();
                index++;
            }

            // Update stock
            PreparedStatement stockStmt = conn.prepareStatement(EcomConstants.UPDATE_STOCK_QTY);
            for (Map.Entry<Product, Integer> entry : items) {
                stockStmt.setInt(1, entry.getValue());
                stockStmt.setInt(2, entry.getKey().getId());
                stockStmt.executeUpdate();
            }

            // Clear customer’s cart
            PreparedStatement clearCartStmt = conn.prepareStatement(EcomConstants.DELETE_FROM_CART_BY_CID);
            clearCartStmt.setInt(1, customer.getId());
            clearCartStmt.executeUpdate();

            conn.commit();

            logger.info("Order placed successfully: " + formattedOrderId + " | Customer: " +
                    IdFormatterUtil.generateCustomerId(customer.getId()));

            return true;

        } catch (SQLException | InsufficientStockException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Rollback failed", ex);
            }
            logger.log(Level.SEVERE, "Error placing order", e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "AutoCommit reset failed", ex);
            }
        }

        return false;
    }


    // Getting All orders by Customer
    @Override
    public List<Map.Entry<Product, Integer>> getOrdersByCustomer(String formattedCustomerId) throws OrderNotFoundException {
        List<Map.Entry<Product, Integer>> orders = new ArrayList<>();
        int customerId = IdFormatterUtil.extractRawId(formattedCustomerId);

        try (PreparedStatement pstmt = conn.prepareStatement(EcomConstants.GET_ORDER_BY_CUST_ID)) {
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product p = extractProductFromResultSet(rs);
                int quantity = rs.getInt("quantity");
                orders.add(new AbstractMap.SimpleEntry<>(p, quantity));
            }
            if (orders.isEmpty()) {
                throw new OrderNotFoundException("No orders found for customer ID: " + formattedCustomerId); // EXCEPTION
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error getting orders by customer", e);
        }
        return orders;
    }



    private Product extractProductFromResultSet(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setProductId(rs.getString("product_id"));
        p.setName(rs.getString("name"));
        p.setPrice(rs.getDouble("price"));
        p.setDescription(rs.getString("description"));
        p.setStockQuantity(rs.getInt("stock_quantity"));
        return p;
    }
}
