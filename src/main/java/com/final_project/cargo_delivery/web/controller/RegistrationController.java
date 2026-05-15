package com.final_project.cargo_delivery.web.controller;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.Role;
import com.final_project.cargo_delivery.service.implementation.LocaleServiceImpl;
import com.final_project.cargo_delivery.service.interfaces.LocaleService;
import com.final_project.cargo_delivery.service.interfaces.UserService;
import com.final_project.cargo_delivery.validator.interfaces.UserValidator;
import com.final_project.cargo_delivery.web.dto.UserCreateDto;
import com.final_project.cargo_delivery.web.dto.UserViewDto;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * RegistrationController.
 *
 * @author Mykhailo Hryb
 */
@Controller
public class RegistrationController {

    private UserValidator userValidator;
    private UserService userService;

    RegistrationController(UserValidator userValidator, UserService userService) {
        this.userValidator = userValidator;
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("/register")
    public void register(HttpServletRequest request) {

        LocaleApplication localeApplication;
        LocaleService localeService = new LocaleServiceImpl();
        Cookie[] arrCookies = request.getCookies();
        localeApplication = localeService.getLocaleByCookieOrDefault(arrCookies);

        UserCreateDto userCreateDto = extractUserFromRequest(request);

        System.out.println("userCreateDto = " + userCreateDto);
        userValidator.validateUserCredentials(localeApplication,
                request.getParameter("email"), request.getParameter("password"));
        userValidator.validateNewUser(localeApplication, userCreateDto);
        UserViewDto userViewDto = userService.register(localeApplication, userCreateDto);
        HttpSession session = request.getSession();
        session.setAttribute("user", userViewDto);
    }

    /**
     * Sets UserCreateDto from user
     *
     * @param request
     * @return UserCreateDto
     */
    private UserCreateDto extractUserFromRequest(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        //Hashing password
        password = DigestUtils.md5Hex(password);
        int role = Role.AUTHORIZED.getID();
        return new UserCreateDto(firstName, lastName, email, role, password);
    }
}

