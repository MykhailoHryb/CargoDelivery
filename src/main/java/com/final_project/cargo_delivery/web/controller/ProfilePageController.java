package com.final_project.cargo_delivery.web.controller;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.service.implementation.LocaleServiceImpl;
import com.final_project.cargo_delivery.service.interfaces.LocaleService;
import com.final_project.cargo_delivery.service.interfaces.OrderService;
import com.final_project.cargo_delivery.web.dto.OrderViewDto;
import com.final_project.cargo_delivery.web.dto.UserViewDto;
import com.final_project.cargo_delivery.web.util.Pagination;
import com.final_project.cargo_delivery.web.util.Sorting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

/**
 * ProfilePageController returns page with orders for user.
 *
 * @author Mykhailo Hryb
 */
@Controller
public class ProfilePageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfilePageController.class);
    private OrderService orderService;

    @Autowired
    ProfilePageController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/profile-page")
    public String getProfilePage(HttpServletRequest request) {

        LocaleApplication localeApplication;
        LocaleService localeService = new LocaleServiceImpl();
        Cookie[] arrCookies = request.getCookies();
        localeApplication = localeService.getLocaleByCookieOrDefault(arrCookies);

        HttpSession session = request.getSession();

        UserViewDto userViewDto = (UserViewDto) session.getAttribute("user");

        int countElements = orderService.getCountElementsByUser(localeApplication, userViewDto.getId());
        Pagination pagination = new Pagination(request, countElements);
        Sorting sorting = new Sorting(request, "order_status_id");

        LocalDate currentDate = LocalDate.now();
        LOGGER.info("current date {}", currentDate);
        orderService.changeStatusByDate(localeApplication, currentDate);

        List<OrderViewDto> orderViewDtoList = orderService.getUserOrders(localeApplication, userViewDto.getId(),
                sorting.getSorting(), sorting.getTypeSorting(), pagination.getStep(), pagination.getPaginationItem());
        request.setAttribute("orderList", orderViewDtoList);

//        LOGGER.info("orderViewDtoList = {}", orderViewDtoList);

        return "profile";
    }
}
