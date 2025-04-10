package com.ecommerce.util;

import com.ecommerce.exception.DBConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionUtil {

    static {
        try {
            Class.forName(DbProperties.getDriver());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    // Establishing Connection

    public static Connection getDbConnection() throws DBConnectionException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DbProperties.getUrl(), DbProperties.getProperties());
        } catch (SQLException e) {
            System.out.println();
            e.printStackTrace();
            throw new DBConnectionException("Cannot connect to database!" + e.getMessage());
        }
        return conn;
    }

    // Closing Connection

    public static void closeConnection(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            }catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

    }
}

