package com.work.dao;

import com.work.entity.Account;
import com.work.entity.User;
import com.work.exception.DAOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserDaoImplTest {
    private static UserDaoImpl userDao;

    @BeforeClass
    public static void open() {
        try {
            userDao = new UserDaoImpl("test.properties");
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void close() {
        try {
            userDao.close();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAll() {
        List<User> userList;
        try {
            userList = userDao.getAll();
            assertEquals(1, userList.get(0).getUserId());
            assertEquals("ira", userList.get(0).getName());
            assertEquals("savchits", userList.get(0).getSureName());
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findById() {
        User user;
        try {
            user = userDao.findById(2);
            assertEquals(2, user.getUserId());
            assertEquals("kirill", user.getName());
            assertEquals("ivanov", user.getSureName());
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUserNameByMaxSum() {
        User user;
        try {
            user = userDao.getUserNameByMaxSum();
            assertEquals(3, user.getName());
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAccountByMaxSum() {
        Account account;
        try {
            account = userDao.getAccountByMaxSum();
            assertEquals(35, account.getAccount());
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}