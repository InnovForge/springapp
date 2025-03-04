/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package springapp.web.controller;

import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springapp.web.dao.UserDao;
import springapp.web.model.HibernateUtil;
import springapp.web.model.Users;

/**
 *
 * @author KunPC
 */
@Controller
@RequestMapping(value = "/admin")
public class LoginController {
    private final UserDao dao = new UserDao();
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        model.addAttribute("user", new Users());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String proccessLogin(@ModelAttribute(value = "user") Users user, ModelMap model, HttpServletRequest request) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        logger.info(user.getUserName() + user.getPassword());
;        if (dao.Login(user.getUserName(), user.getPassword())) {
            model.put("message", "<div class=\"alert alert-error\">That Admin account doesn't exist.<button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button></div>");
            return "login";
        } else {
            request.getSession().setAttribute("LOGGEDIN_USER", user);
            request.getSession().setAttribute("USER", user.getUserName());
            return "redirect:dashboard.html";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model, HttpServletRequest request) {
        request.getSession().removeAttribute("LOGGEDIN_USER");
        model.addAttribute("user", new Users());
        return "redirect:login.html";
    }
}
