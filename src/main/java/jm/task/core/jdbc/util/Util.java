package jm.task.core.jdbc.util;

import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Driver;
import java.sql.Connection;

public class Util {
    // реализуйте настройку соеденения с БД

    // Статические переменные для хранения настроек подключения к БД
    private final static String DB_URL = "jdbc:mysql://localhost:3306/mydbtest"; // Адрес базы данных
    private final static String USER = "admin";                                  // Имя пользователя
    private final static String PASSWORD = "admin";                              // Пароль

    public static void main(String[] args) {
        try {
            // Создаем экземпляр класса драйвера MySQL Connector
            Driver driver = new com.mysql.cj.jdbc.Driver();
            // Регистрируем драйвер JDBC
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            // Обработка исключений SQL (например, неверное подключение)
            System.err.println("Не удалось загрузить класс драйвера!");
        }
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD); // Получаем соединение с базой данных используя предоставленные настройки
            Statement statement = connection.createStatement();) {

        }
    }
}
