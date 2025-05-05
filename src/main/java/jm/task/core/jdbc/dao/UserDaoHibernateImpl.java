package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jm.task.core.jdbc.util.Util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {

    private final Logger logger = Logger.getLogger(getClass().getName()); // Логгер
    String sql;
    private static Transaction transaction = null;


    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {

        sql = "CREATE TABLE IF NOT EXISTS `users` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastName` VARCHAR(45) NOT NULL,\n" +
                "  `age` TINYINT NULL,\n" +
                "  PRIMARY KEY (`id`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;";
        //Transaction transaction = null;


        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            logger.log(Level.INFO, "Таблица 'users' успешно создана.");
            transaction.commit();
        } catch (HibernateException e) { // Ловим общие исключения
            logger.log(Level.SEVERE, "Ошибка создания таблицы: ", e);
            e.printStackTrace(); // Вывести трассировку стека для диагностики
        }
    }

    @Override
    public void dropUsersTable() {
        sql = "DROP TABLE IF EXISTS users";
        //Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            logger.log(Level.INFO, "Таблица 'users' успешно удалена.");
            transaction.commit();
        } catch (HibernateException e) { // Ловим общие исключения
            logger.log(Level.SEVERE, "Ошибка удаления таблицы: ", e);
            e.printStackTrace(); // Вывести трассировку стека для диагностики

            // Попытка отката транзакции, если возникла ошибка

            try {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
            } catch (HibernateException rollbackEx) {
                logger.log(Level.SEVERE, "Ошибка отката транзакции: ", rollbackEx);
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        //Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Создаём экземпляр объекта User и сохраняем его
            User user = new User(name, lastName, age);
            session.save(user);

            logger.log(Level.INFO, "Пользователь успешно сохранён.");
            transaction.commit();
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Ошибка сохранения пользователя: ", e);
            e.printStackTrace(); // Диагностика ошибок

            // Откат транзакции в случае ошибки
            try {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
            } catch (HibernateException rollbackEx) {
                logger.log(Level.SEVERE, "Ошибка отката транзакции: ", rollbackEx);
            }
        }
    }

    @Override
    public void removeUserById(long id) {

        //Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // Формирование HQL-запроса для удаления пользователя по id
            Query<?> query = session.createQuery("DELETE FROM User WHERE id = :userId")
                    .setParameter("userId", id);
            //User user = (User) session.get(User.class,id);
            int rowsDeleted = query.executeUpdate();

            if (rowsDeleted > 0) {
                logger.log(Level.INFO, "Пользователь с id={0} успешно удалён.", id);
            } else {
                logger.log(Level.WARNING, "Пользователь с id={0} не найден или не удалён.", id);
            }

            transaction.commit();
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Ошибка удаления пользователя с id={0}: {1}", new Object[]{id, e.getMessage()});
            e.printStackTrace(); // Вывести трассировку стека для диагностики

            // Попытка отката транзакции, если возникла ошибка
            try {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
            } catch (HibernateException rollbackEx) {
                logger.log(Level.SEVERE, "Ошибка отката транзакции: ", rollbackEx);
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        //List<User> userList = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            // Используем HQL-запрос для выборки всех пользователей
            return session.createQuery("FROM User", User.class).list();
            //userList = session.createQuery("FROM User", User.class).getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
            return Collections.emptyList(); // Возврат пустого списка при ошибке
        }
        //return userList;
    }
    public void cleanUsersTable() {
        //Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Используем HQL-запрос для очистки таблицы
            Query<?> query = session.createSQLQuery("TRUNCATE TABLE users");
            query.executeUpdate();
            //session.createSQLQuery("TRUNCATE TABLE users;").executeUpdate();

            logger.log(Level.INFO, "Таблица успешно очищена.");
            transaction.commit();
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Ошибка очистки таблицы: ", e);
            e.printStackTrace();

            // Попытка отката транзакции, если возникла ошибка
            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (HibernateException rollbackEx) {
                    logger.log(Level.SEVERE, "Ошибка отката транзакции: ", rollbackEx);
                }
            }
        }
    }
}