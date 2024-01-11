package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteManager {

    private static final String DB_CLASS = "org.sqlite.JDBC";
    private static final String DB_URL = "jdbc:sqlite:book.db";

    private static Connection connection;

    private SQLiteManager() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            createConnection();
        }
        return connection;
    }

    private static void createConnection() throws SQLException {
        try {
            Class.forName(DB_CLASS);
            connection = DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("[SQLiteManager : createConnection()] " + e.getMessage());
            throw new SQLException("Failed to connect to the database", e);
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("[SQLiteManager : closeConnection()]" + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
}

