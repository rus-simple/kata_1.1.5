package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    //создание таблицы
    public void createUsersTable() {

        String sqlcreateUsersTable = "CREATE TABLE `katadbusers`.`users` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastName` VARCHAR(45) NOT NULL,\n" +
                "  `age` TINYINT NULL,\n" +
                "  PRIMARY KEY (`id`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sqlcreateUsersTable)) {
            preparedStatement.execute(); // Выполнение команды
            System.out.println("Таблица успешно создана.");
        } catch (SQLException e) {
            System.err.println("Ошибка создания таблицы: " + e.getMessage());
        }
    }

    //удаление таблицы
    public void dropUsersTable() {

        String sqldropUsersTable = "DROP TABLE IF EXISTS katadbusers.users";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sqldropUsersTable)) {
            preparedStatement.execute();
            System.out.println("Таблица успешно удалена.");
        } catch (SQLException e) {
            System.err.println("Ошибка удаления таблицы: " + e.getMessage());
        }
    }

    //Добавление User в таблицу
    public void saveUser(String name, String lastName, byte age) {
        User user = new User();

        String sqlsaveUser = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";

        try(PreparedStatement preparedStatement = getConnection().prepareStatement(sqlsaveUser)) {
            preparedStatement.setString(1, name); // передача фактического имени
            preparedStatement.setString(2, lastName); // передача фамилии
            preparedStatement.setByte(3, age); // передача возраста

            preparedStatement.executeUpdate();
            System.out.println("User с именем — " + name + " добавлен в базу данных.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка сохранения пользователя: " + e.getMessage());
        }

    }

    //удаление из таблицы по id
    public void removeUserById(long id) {
        String sqlremoveUserById = "DELETE from users where id = ?";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sqlremoveUserById))  {
            //PreparedStatement preparedStatement = null;
            //preparedStatement = getConnection().prepareStatement(sqlremoveUserById);
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь успешно удалён.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка удаления пользователя: " + e.getMessage());
        }
    }

    //Получение всех User(ов) из таблицы
    public List<User> getAllUsers() {
        List<User> listUsers = new ArrayList<>();
        String sqlgetAllUsers = "SELECT * from users";
        //Statement statement = null;

        try(Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlgetAllUsers);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                listUsers.add(user);
            }
            if (!listUsers.isEmpty()) {
                System.out.println("Данные таблицы выгружены!");
            } else {
                System.out.println("Нет данных в таблице.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка получения данных таблицы!");
        }
        return listUsers;
    }


    //Очистка содержания таблицы
    public void cleanUsersTable() {
        User user = new User();
        String sqlcleanUsersTable = "TRUNCATE TABLE users";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sqlcleanUsersTable)) {
            preparedStatement.execute();
            System.out.println("Таблица успешно очищена.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка очистки таблицы: " + e.getMessage());
        }
    }
}
