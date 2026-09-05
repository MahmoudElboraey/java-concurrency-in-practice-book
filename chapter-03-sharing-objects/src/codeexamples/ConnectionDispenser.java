package codeexamples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDispenser {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/mydatabase";

    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            try {
                return DriverManager.getConnection(DB_URL);
            } catch (SQLException e) {
                throw new RuntimeException("Unable to acquire Connection, e", e);
            }
        }
    };

    public static Connection getConnection() {
        return connectionHolder.get();
    }
}
