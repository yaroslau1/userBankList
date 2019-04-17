package com.work.listeners;

import com.work.dao.UserDaoImpl;
import com.work.exception.DAOException;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent sessionEvent) {
        System.out.println("Session Created:: ID=" + sessionEvent.getSession().getId());
        sessionEvent.getSession().setAttribute("name", "Session");
        try {
            UserDaoImpl userDao = new UserDaoImpl();
            sessionEvent.getSession().setAttribute("userConnect", userDao);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        System.out.println("Session Destroyed:: ID=" + sessionEvent.getSession().getId());
        try {
            UserDaoImpl userDao = (UserDaoImpl) sessionEvent.getSession().getAttribute("userConnect");
            userDao.close();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
