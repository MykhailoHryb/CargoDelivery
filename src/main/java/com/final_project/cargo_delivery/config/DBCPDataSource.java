package com.final_project.cargo_delivery.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Database connection pull data source
 *
 * @author Mykhailo Hryb
 */
public class DBCPDataSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBCPDataSource.class);
    private static final BasicDataSource ds = new BasicDataSource();

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/db.properties"));
            String dbUrl = properties.getProperty("url");
            String dbUser = properties.getProperty("username");
            String dbPassword = properties.getProperty("password");
            ds.setUrl(dbUrl);
            ds.setUsername(dbUser);
            ds.setPassword(dbPassword);
            ds.setMinIdle(1);
            ds.setMaxIdle(5);
            ds.setMaxTotal(10);
            ds.setMaxOpenPreparedStatements(100);
        } catch (IOException e) {
            LOGGER.error("Exception while loading db properties, {}", e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private DBCPDataSource() {
    }
}
