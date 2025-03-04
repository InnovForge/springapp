/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package springapp.web.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import springapp.web.controller.AuthController;
import springapp.web.model.HibernateUtil;
import springapp.web.model.Users;
import springapp.web.ws.SocketHandler;

/**
 *
 * @author AnhDao
 */
public class UserDao extends Users {

    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
    private final SocketHandler webSocketHandler = new SocketHandler();

    public Boolean CheckUser(String username) {
        Boolean returnBool;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            List lstUser = session.createQuery("from Users where User_Name=:username").setParameter("username", username).list();
            returnBool = !lstUser.isEmpty();
            session.getTransaction().commit();
        } catch (Exception e) {
            returnBool = false;
        }
        return returnBool;
    }

    public Boolean Login(String username, String password) {
        Boolean returnBool;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            List lstUser = session.createQuery("from Users where User_name=:username and Password=:password").setParameter("username", username).setParameter("password", password).list();
            returnBool = !lstUser.isEmpty();

            session.getTransaction().commit();
        } catch (Exception e) {
            returnBool = false;
        }
        return returnBool;
    }

    public List<Users> listUser() {
        List<Users> list;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            list = session.createQuery("from Users").list();
        } catch (Exception e) {
            list = null;
        }
        return list;
    }

    public void insertUser(Users user) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Users newUser = new Users(user.getUserName(), user.getPassword(), user.getEmail(), user.isActive());
            session.save(newUser);
            transaction.commit();

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonResponse = new HashMap<String, Object>();
            jsonResponse.put("server", "sringapp");
            jsonResponse.put("message", "New user added successfully!");
            jsonResponse.put("user", newUser);

            String jsonString = objectMapper.writeValueAsString(jsonResponse);
            webSocketHandler.sendMessageToAll(jsonString);
            System.out.println("User inserted successfully!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
