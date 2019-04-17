package com.work.dao;

import com.work.entity.Account;
import com.work.entity.User;
import com.work.exception.DAOException;

import java.util.List;

public interface UserDao {
    public List<User> getAll() throws DAOException;

    public User findById(int userId) throws DAOException;

    public User getUserNameByMaxSum() throws DAOException;

    public Account getAccountByMaxSum() throws DAOException;
}
