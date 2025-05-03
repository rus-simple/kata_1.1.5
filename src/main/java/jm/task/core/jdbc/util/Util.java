package jm.task.core.jdbc.util;

import java.sql.DriverManager;
//import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Driver;
import java.sql.Connection;

public class Util {
    // Настройки подключения к базе данных
    private final static String DB_URL = "jdbc:mysql://localhost:3306/katadbusers";
    private final static String USER = "admin";
    private final static String PASSWORD = "admin";

    /** * Метод получает соединение с базой данных. */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("Подключение к базе данных установлено.");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к базе данных: " + e.getMessage());
        }
    }
}
