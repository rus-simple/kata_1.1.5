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
        PreparedStatement preparedStatement = null;
        String sqlcreateUsersTable = "CREATE TABLE `katadbusers`.`users` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastName` VARCHAR(45) NOT NULL,\n" +
                "  `age` INT(3) NULL,\n" +
                "  PRIMARY KEY (`id`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;";
        try {
            preparedStatement = getConnection().prepareStatement(sqlcreateUsersTable);
            System.out.println("Таблица создана!");
        } catch (SQLException e) {
            System.err.println("Ошибка создания таблицы");
        }
    }

    //удаление таблицы
    public void dropUsersTable() {

        String sqldropUsersTable = "DROP TABLE IF EXISTS katadbusers.users";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement(sqldropUsersTable);
            System.out.println("Таблица удалена!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка удаления таблицы!");
        }
    }

    //Добавление User в таблицу
    public void saveUser(String name, String lastName, byte age) {
        User user = new User();
        //user.name = name;
        //User.lastName = lastName;
        //User.age = age;

        String sqlsaveUser = "INSERT INTO users (id, name, lastName, age) VALUES (?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = getConnection().prepareStatement(sqlsaveUser)) {
            //preparedStatement = ;

            preparedStatement.setLong(1,user.getId());
            preparedStatement.setString(2,user.getName());
            preparedStatement.setString(3,user.getLastName());
            preparedStatement.setByte(3,user.getAge());

            preparedStatement.executeUpdate();
            System.out.println("Запись добавлена в таблицу!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка создания пользователя!");
        }

    }

    //удаление из таблицы по id
    public void removeUserById(long id) {
        String sqlremoveUserById = "DELETE from users where id = ?";
        User user = new User();

        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = getConnection().prepareStatement(sqlremoveUserById);
            preparedStatement.setLong(1,id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка удаления из таблицы!");
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
                System.out.println("Данные таблицы выгружены!");
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
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = getConnection().prepareStatement(sqlcleanUsersTable) ;
            System.out.println("Таблица удалена!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка очистки таблицы!");
        }
    }
}
