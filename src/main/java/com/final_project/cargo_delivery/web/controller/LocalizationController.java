package com.final_project.cargo_delivery.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * LocalizationController sets localization from user.
 *
 * @author Mykhailo Hryb
 */
@Controller
public class LocalizationController {

    @PostMapping("/localization")
    public void setLocalization(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Cookie langCookie = new Cookie("lang", request.getParameter("lang"));
        if (request.getParameter("lang").equals("en_EN")) {
            langCookie.setMaxAge(0);
        } else {
            langCookie.setMaxAge(60 * 60 * 24 * 7);
        }
        response.addCookie(langCookie);
    }
}
