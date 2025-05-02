package jm.task.core.jdbc.util;

import java.sql.DriverManager;
//import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Driver;
import java.sql.Connection;

public class Util {
    // реализуйте настройку соединения с БД

    // Статические переменные для хранения настроек подключения к БД

    //private final static String DB_DRIVER = "com.mysql.cj.jdbc.Driver()";                                  // Имя пользователя
    private final static String DB_URL = "jdbc:mysql://localhost:3306/katadbusers"; // Адрес базы данных
    private final static String USER = "admin";                                  // Имя пользователя
    private final static String PASSWORD = "admin";                              // Пароль

    public static Connection getConnection() {

        try {
            // Создаем экземпляр класса драйвера MySQL Connector
            Driver driver = new com.mysql.cj.jdbc.Driver();
            // Регистрируем драйвер JDBC
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            // Обработка исключений SQL (например, неверное подключение)
            System.err.println("Не удалось загрузить класс драйвера!");
        }

        Connection connection = null;
        try { // Получаем соединение с базой данных используя предоставленные настройки

            // Создаем экземпляр класса драйвера MySQL Connector
            //Driver driver = new com.mysql.cj.jdbc.Driver();
            // Регистрируем драйвер JDBC
            //DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Connection DB open!");   // Если соединение открыто, выводим сообщение
            }
        } catch (SQLException e) {
            System.err.println("Error DB connection!");
        }
        return connection;
    }
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        //Utils util = new Utils();
        //util.
        getConnection();
    }
}
