package com.final_project.cargo_delivery.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * LogOutController.
 *
 * @author Mykhailo Hryb
 */
@Controller
public class LogOutController {

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("user") != null) {
            session.removeAttribute("user");
        }
        return "";
    }
}
