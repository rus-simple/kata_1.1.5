package jm.task.core.jdbc.service;

import com.sun.source.util.SourcePositions;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    public void createUsersTable() {
        createUsersTable();
    }

    public void dropUsersTable() {
        dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        saveUser(name, lastName, age);
        System.out.println("User с именем — " + name + " добавлен в базу данных.");
        saveUser(name, lastName, age);
        System.out.println("User с именем — " + name + " добавлен в базу данных.");
        saveUser(name, lastName, age);
        System.out.println("User с именем — " + name + " добавлен в базу данных.");
        saveUser(name, lastName, age);
        System.out.println("User с именем — " + name + " добавлен в базу данных.");
    }

    public void removeUserById(long id) {
        removeUserById(id);
    }

    public List<User> getAllUsers() {
        getAllUsers();
        return null;
    }

    public void cleanUsersTable() {
        cleanUsersTable();
    }
}
