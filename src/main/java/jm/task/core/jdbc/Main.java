package jm.task.core.jdbc;

import antlr.Utils;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // Инстанцируем сервис для работы с пользователями
        UserServiceImpl userService = new UserServiceImpl();

        // Шаг 1: Создаём таблицу
        userService.createUsersTable();
        System.out.println("Таблица успешно создана.");

        // Шаг 2: Добавляем пользователя
        userService.saveUser("Иван", "Иванов", (byte) 30);
        userService.saveUser("Петр", "Петров", (byte) 20);
        userService.saveUser("Алексей", "Алексеева", (byte) 25);
        userService.saveUser("Андрей", "Андреев", (byte) 35);
        //System.out.println("User с именем — " + name + " добавлен в базу данных.");

        // Шаг 3: Получаем всех пользователей
        userService.getAllUsers();

        // Шаг 4: Чистим таблицу
        userService.cleanUsersTable();
        System.out.println("Таблица успешно очищена.");

        // Шаг 5: Удаляем таблицу
        userService.dropUsersTable();
        System.out.println("Таблица успешно удалена.");

    }
}
