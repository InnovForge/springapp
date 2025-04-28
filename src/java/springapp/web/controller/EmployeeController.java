/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package springapp.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springapp.web.dao.EmployeeDao;
import springapp.web.kafka.KafkaMessageProducer;
import springapp.web.model.Employee;
import springapp.web.model.HibernateUtil;
import springapp.web.model.Users;

/**
 *
 * @author
 */
@Controller
@RequestMapping(value = "/admin")
public class EmployeeController {

    KafkaMessageProducer kafka = new KafkaMessageProducer();
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeDao dao = new EmployeeDao();

    @RequestMapping(value = {"/employee/list"}, method = RequestMethod.GET)
    public String listUsers(ModelMap model, HttpServletRequest request) {
        Users user = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        String value = "";
        if (user != null) {
            try {
                Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                List listEmployees = session.createQuery("from Employee").list();
                model.addAttribute("listEmployees", listEmployees);
                session.getTransaction().commit();
                value = "admin/listEmployee";
            } catch (Exception e) {
                value = "admin/listEmployee";
            }

        } else {
            model.addAttribute("user", new Users());

        }
        return value;
    }

    @RequestMapping(value = "/employee/add", method = RequestMethod.GET)
    public String formEmployee(ModelMap model, HttpServletRequest request) {
        Users user = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        if (user != null) {
            model.addAttribute("employee", new Employee());
            return "admin/addEmployee";
        } else {
            model.addAttribute("user", new Users());
            return "redirect:/admin/login.html";
        }

    }

    @RequestMapping(value = "/employee/add", method = RequestMethod.POST)
    public String addEmplyees(@ModelAttribute(value = "employee") Employee emp, ModelMap model, HttpServletRequest request) {
        Users userSession = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        logger.info("created is run");
        if (userSession != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonMessage = objectMapper.writeValueAsString(emp);
                kafka.send("payroll-created", jsonMessage);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("error call kafka");
            }
            dao.insertEmployee(emp);
            return "redirect:/admin/employee/list.html";
        } else {
            model.addAttribute("user", new Users());
            return "redirect:/admin/login.html";
        }

    }

    @RequestMapping(value = "/employee/update", method = RequestMethod.GET)
    public String formEmployeeUpdate(ModelMap model, HttpServletRequest request) {
        Users user = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        if (user != null) {
            model.addAttribute("employee", new Employee());
            return "admin/updateEmployee";
        } else {
            model.addAttribute("user", new Users());
            return "redirect:/admin/login.html";
        }

    }

    @RequestMapping(value = "/employee/update", method = RequestMethod.POST)
    public String updateEmplyeed(@ModelAttribute(value = "employee") Employee emp, ModelMap model, HttpServletRequest request) {
        Users userSession = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        logger.info("update is run");
        if (userSession != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonMessage = objectMapper.writeValueAsString(emp);
                kafka.send("payroll-updated", jsonMessage);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("error call kafka");
            }
            dao.updateEmployeeById(emp);
            return "redirect:/admin/employee/list.html";
        } else {
            model.addAttribute("user", new Users());
            return "redirect:/admin/login.html";
        }

    }
    
    @RequestMapping(value = "/employee/delete", method = RequestMethod.GET)
    public String formEmployeeDelete(ModelMap model, HttpServletRequest request) {
        Users user = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        if (user != null) {
            model.addAttribute("employee", new Employee());
            return "admin/deleteEmployee";
        } else {
            model.addAttribute("user", new Users());
            return "redirect:/admin/login.html";
        }

    }

    @RequestMapping(value = "/employee/delete", method = RequestMethod.POST)
    public String deleteEmplyee(@ModelAttribute(value = "employee") Employee emp, ModelMap model, HttpServletRequest request) {
        Users userSession = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        logger.info("update is run");
        if (userSession != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonMessage = objectMapper.writeValueAsString(emp);
                kafka.send("payroll-deleted", jsonMessage);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("error call kafka");
            }
            dao.deleteEmployeeById(emp.getIdEmployee());
            return "redirect:/admin/employee/list.html";
        } else {
            model.addAttribute("user", new Users());
            return "redirect:/admin/login.html";
        }

    }
}

//@Controller
//@RequestMapping(value = "/admin")
//public class EmployeeController {
//    @RequestMapping(value = "/employee/list", method = RequestMethod.GET)
//    public String listEmployee(ModelMap model, HttpServletRequest request) {
//        Users user = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
//        String value = "";
//        if (user != null) {
//            try {
//                Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//                session.beginTransaction();
//                List listEmployees = session.createQuery("from Employee").list();
//                model.addAttribute("listEmployees", listEmployees);
//                session.getTransaction().commit();
//                value = "admin/listEmployee";
//            } catch (Exception e) {
//            }
//
//        } else {
//            model.addAttribute("user", new Users());
//            value= "redirect:/admin/login.html";
//        }
//        return value;
//    }
//}
