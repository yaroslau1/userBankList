package com.work.dao;

import com.work.entity.Account;
import com.work.entity.User;
import com.work.exception.DAOException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class UserDaoImpl implements UserDao, AutoCloseable {

    private Connection connection;
    private PreparedStatement getAll;
    private PreparedStatement getUserByMaxSum;

    public UserDaoImpl() throws DAOException {
        Properties property = new Properties();
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream reader = classLoader.getResourceAsStream("properties.properties");
        try {
            property.load(reader);
            String driverName = property.getProperty("driver");
            String url = property.getProperty("url");
            String user = property.getProperty("user");
            String pass = property.getProperty("pass");
            reader.close();
            Class.forName(driverName).newInstance();
            connection = DriverManager.getConnection(url, user, pass);
            getAll = connection.prepareStatement("SELECT userId, name, sureName FROM user");
            getUserByMaxSum = connection.prepareStatement("SELECT userId, SUM(account) AS total FROM account GROUP BY userId ORDER BY total DESC LIMIT 1");
        } catch (IOException e) {
            throw new DAOException("Error in constructor with file opening", e);
        } catch (InstantiationException e) {
            throw new DAOException("Error in constructor: object Class not found", e);
        } catch (IllegalAccessException e) {
            throw new DAOException("Error in constructor with access to DB", e);
        } catch (ClassNotFoundException e) {
            throw new DAOException("Error in constructor with ClassPath", e);
        } catch (SQLException e) {
            throw new DAOException("Error in constructor with SQLQuery", e);
        }
    }

    public UserDaoImpl(String filePath) throws DAOException {
        Properties property = new Properties();
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream reader = classLoader.getResourceAsStream(filePath);
        try {
            property.load(reader);
            String driverName = property.getProperty("driver");
            String url = property.getProperty("url");
            String user = property.getProperty("user");
            String pass = property.getProperty("pass");
            reader.close();
            Class.forName(driverName).newInstance();
            connection = DriverManager.getConnection(url, user, pass);
            getAll = connection.prepareStatement("SELECT userId, name, sureName FROM user");
            getUserByMaxSum = connection.prepareStatement("SELECT userId, SUM(account) AS total FROM account GROUP BY userId ORDER BY total DESC LIMIT 1");
        } catch (IOException e) {
            throw new DAOException("Error in constructor with file opening", e);
        } catch (InstantiationException e) {
            throw new DAOException("Error in constructor: object Class not found", e);
        } catch (IllegalAccessException e) {
            throw new DAOException("Error in constructor with access to DB", e);
        } catch (ClassNotFoundException e) {
            throw new DAOException("Error in constructor with ClassPath", e);
        } catch (SQLException e) {
            throw new DAOException("Error in constructor with SQLQuery", e);
        }
    }

    @Override
    public List<User> getAll() throws DAOException {
        List<User> userList = new LinkedList<>();
        try (ResultSet resultSet = getAll.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                String userId = resultSet.getString("userId");
                String name = resultSet.getString("name");
                String sureName = resultSet.getString("sureName");
                user.setName(name);
                user.setSureName(sureName);
                user.setUserId(Integer.parseInt(userId));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new DAOException("Error in getAll method", e);
        }
    }

    @Override
    public User findById(int userId) throws DAOException {
        User user = new User();
        try (ResultSet resultSet = getAll.executeQuery()) {
            while (resultSet.next()) {
                int idForSearch = Integer.parseInt(resultSet.getString("userId"));
                if (idForSearch == userId) {
                    String name = resultSet.getString("name");
                    String sureName = resultSet.getString("sureName");
                    user.setName(name);
                    user.setSureName(sureName);
                    user.setUserId(userId);
                }
            }
            return user;
        } catch (SQLException e) {
            throw new DAOException("Error in findById method", e);
        }
    }

    @Override
    public User getUserNameByMaxSum() throws DAOException {
        User user = new User();
        try (ResultSet resultSet = getUserByMaxSum.executeQuery()) {
            while (resultSet.next()) {
                String userId = resultSet.getString("userId");
                user = findById(Integer.parseInt(userId));
            }
            return user;
        } catch (SQLException e) {
            throw new DAOException("Error in getUserNameByMaxSum method", e);
        }
    }

    @Override
    public Account getAccountByMaxSum() throws DAOException {
        Account account = new Account();
        try (ResultSet resultSet = getUserByMaxSum.executeQuery()) {
            while (resultSet.next()) {
                String total = resultSet.getString("total");
                account.setAccount(Integer.parseInt(total));
            }
            return account;
        } catch (SQLException e) {
            throw new DAOException("Error in getAccountByMaxSum method", e);
        }
    }

    @Override
    public void close() throws DAOException {
        SQLException exception = new SQLException("Some errors with closing");
        if (getAll != null) {
            try {
                getAll.close();
            } catch (SQLException e) {
                exception.addSuppressed(e);
            }
        }
        if (getUserByMaxSum != null) {
            try {
                getUserByMaxSum.close();
            } catch (SQLException e) {
                exception.addSuppressed(e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                exception.addSuppressed(e);
            }
        }
        if (exception.getSuppressed().length > 0) {
            throw new DAOException("errors with closing PrepereStatement in user dao", exception);
        }

    }
}
