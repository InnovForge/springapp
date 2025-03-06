/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package springapp.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springapp.web.dao.UserDao;
import springapp.web.model.Users;

/**
 *
 * @author KunPC
 */
@Controller
@RequestMapping(value = "/admin")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
 
    private final UserDao dao = new UserDao();

    @RequestMapping(value = "/user/list")
    public String listUsers(ModelMap model, HttpServletRequest request) {
        Users user = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        String value;
        if (user != null) {
            List<Users> listUsers = dao.listUser();
            model.addAttribute("listUsers", listUsers);
            value = "admin/listUser";
        } else {
            model.addAttribute("user", new Users());
            value = "redirect:/admin/login.html";
        }
        return value;
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String addUsers(@ModelAttribute(value = "user") Users user, ModelMap model, HttpServletRequest request) {
        Users userSession = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        logger.info("created is run");
        if (userSession != null) {
            if (dao.CheckUser(user.getUserName())) {
                model.addAttribute("user", user);
                return "admin/listUser";
            } else {
                dao.insertUser(user);
                return "redirect:/admin/user/list.html";
//                  return "admin/listUser";
            }
        } else {
            model.addAttribute("user", new Users());
            return "redirect:/admin/login.html";
        }

    }

    @RequestMapping(value = "/user/add", method = RequestMethod.GET)
    public String formUsers(ModelMap model, HttpServletRequest request) {
        Users user = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        if (user != null) {
            model.addAttribute("user", new Users());
            return "admin/addUser";
        } else {
            model.addAttribute("user", new Users());
            return "redirect:/admin/login.html";
        }
    }
    
//     @RequestMapping(value = "/user/employee", method = RequestMethod.GET)
//    public String formEmployee(ModelMap model, HttpServletRequest request) {
//         Users user = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
//            if (user != null) {
//            model.addAttribute("user", new Users());
//            return "admin/addEmployee";
//        } else {
//            model.addAttribute("user", new Users());
//            return "redirect:/admin/login.html";
//        }
//
//    }
}
