package com.final_project.cargo_delivery.web.controller;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.exception.ApplicationException;
import com.final_project.cargo_delivery.service.implementation.LocaleServiceImpl;
import com.final_project.cargo_delivery.service.interfaces.LocaleService;
import com.final_project.cargo_delivery.service.interfaces.UserService;
import com.final_project.cargo_delivery.validator.interfaces.UserValidator;
import com.final_project.cargo_delivery.web.dto.UserViewDto;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * LoginController.
 *
 * @author Mykhailo Hryb
 */
@Controller
public class LoginController {

    private UserService userService;
    private UserValidator userValidator;

    @Autowired
    LoginController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/login-page")
    public String getLoginPage(){
        return "login";
    }

    @PostMapping("/login")
    public void login(HttpServletRequest request) throws IOException, ServletException {

        LocaleApplication localeApplication;
        LocaleService localeService = new LocaleServiceImpl();
        Cookie[] arrCookies = request.getCookies();
        localeApplication = localeService.getLocaleByCookieOrDefault(arrCookies);

        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            throw new ApplicationException("You have already logged in");
        }

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        //Hashing password
        password = DigestUtils.md5Hex(password);

        UserViewDto userViewDto = userService.login(localeApplication, email, password);
        userValidator.validateUserCredentials(localeApplication, email, password);
        userValidator.isCorrectPasswordAndEmail(localeApplication, userViewDto, email, password);
        session.setAttribute("user", userViewDto);
    }
}
