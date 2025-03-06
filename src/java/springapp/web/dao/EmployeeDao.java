/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package springapp.web.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import springapp.web.model.Employee;
import springapp.web.model.HibernateUtil;
import springapp.web.ws.SocketHandler;

/**
 *
 * @author ngtuonghy
 */
public class EmployeeDao extends Employee {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeDao.class);
    private final SocketHandler webSocketHandler = new SocketHandler();

    public void insertEmployee(Employee emp) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Employee newEmp = new Employee(emp.getEmployeeNumber(),emp.getIdEmployee(), emp.getLastName(), emp.getFirstName(),emp.getSsn(), emp.getPayRate(),emp.getPayRatesId(), emp.getVacationDays(),emp.getPaidToDate(),emp.getPaidLastYear());
            session.save(newEmp);
            transaction.commit();
            
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonResponse = new HashMap<String, Object>();
            jsonResponse.put("server", "sringapp");
            jsonResponse.put("message", "New employee added successfully!");
            jsonResponse.put("data", newEmp);
            String jsonString = objectMapper.writeValueAsString(jsonResponse);
            
            webSocketHandler.sendMessageToAll(jsonString);
            System.out.println("Employee inserted successfully!");
        } catch (Exception e) {
            
         logger.error(e.toString());
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
