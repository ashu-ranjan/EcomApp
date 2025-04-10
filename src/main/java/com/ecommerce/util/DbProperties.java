package com.ecommerce.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbProperties {
    private static Properties properties = new Properties();

    static {
        try {

            InputStream in = DbProperties.class.getClassLoader().getResourceAsStream("ecommerce.properties");
            properties.load(in);
            if (in == null) {
                throw new RuntimeException("Resource file not found!");
            }


        } catch (IOException e) {
            throw new RuntimeException("Database Connection Failed!", e);
        }
    }

    public static String getDriver() {
        return properties.getProperty(EcomConstants.GET_DRIVER);
    }

    public static String getUrl() {
        return properties.getProperty(EcomConstants.GET_URL);
    }

    public static String getUser() {
        return properties.getProperty(EcomConstants.GET_USER);
    }

    public static String getPassword() {
        return properties.getProperty(EcomConstants.GET_PASSWORD);
    }

    public static Properties getProperties() {
        Properties connProperties = new Properties();
        connProperties.put("user", getUser());
        connProperties.put("password", getPassword());
        return connProperties;
    }
}

