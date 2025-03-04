/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package springapp.web.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;
import java.util.TimeZone;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ngtuonghy
 */
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

  private static final String SECRET_KEY = "0123456789abcdef0123456789abcdef"; // 32-byte key
  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
 
  public static String encrypt(String data) throws Exception {
        // Băm dữ liệu bằng HMAC-SHA256
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKey);
        byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

        String signature = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        String payload = Base64.getUrlEncoder().withoutPadding().encodeToString(data.getBytes(StandardCharsets.UTF_8));

        return signature + "." + payload;
    }
    @RequestMapping(value = "/sso", method = RequestMethod.GET)
    public String listUsers(ModelMap model, HttpServletRequest request) throws Exception {
        String redirect_uri = "http://localhost:19335/auth/sso";
         String username = request.getParameter("username");
         Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        long timestamp = cal.getTimeInMillis() / 1000 ; 
        String data = username + "|" + timestamp;
        logger.info(redirect_uri + "?token=" + encrypt(data));
        return redirect_uri + "?token=" + encrypt(data);
    }
}
