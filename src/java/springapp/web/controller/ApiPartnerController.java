/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package springapp.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springapp.web.dao.UserDao;
import springapp.web.model.Users;

/**
 *
 * @author ngtuonghy
 */
@RestController
@RequestMapping("api/v1/partner")
public class ApiPartnerController {
     private static final Logger logger = LoggerFactory.getLogger(ApiPartnerController.class);
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getUser(@RequestBody Map<String, Object> request, @RequestParam("token") String token) {
        Map<String, Object> response = new HashMap<String,Object>();
        logger.error("demo err");
     
        if (!token.equals("12345")) {
            response.put("status", "error");
            response.put("message", "Unauthorized: Invalid token");
            response.put("data", null);
            logger.info(response.toString());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.UNAUTHORIZED);

        }
        UserDao userDAO = new UserDao();
        List<Users> users = userDAO.listUser();
        response.put("status", "success");
        response.put("message", "Request processed successfully");
        response.put("data", users);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }
}
