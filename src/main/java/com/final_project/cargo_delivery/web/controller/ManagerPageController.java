package com.final_project.cargo_delivery.web.controller;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.service.implementation.LocaleServiceImpl;
import com.final_project.cargo_delivery.service.interfaces.LocaleService;
import com.final_project.cargo_delivery.service.interfaces.OrderService;
import com.final_project.cargo_delivery.web.dto.OrderViewDto;
import com.final_project.cargo_delivery.web.util.Pagination;
import com.final_project.cargo_delivery.web.util.Sorting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * ManagerPageController returns page with all orders for manager.
 *
 * @author Mykhailo Hryb
 */
@Controller
public class ManagerPageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerPageController.class);
    private OrderService orderService;

    @Autowired
    ManagerPageController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/manager-page")
    public String getManagerPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LocaleApplication localeApplication;
        LocaleService localeService = new LocaleServiceImpl();
        Cookie[] arrCookies = request.getCookies();
        localeApplication = localeService.getLocaleByCookieOrDefault(arrCookies);

        int countElements = orderService.getCountElements(localeApplication);
        Pagination pagination = new Pagination(request, countElements);
        Sorting sorting = new Sorting(request, "order_status_id");

        LocalDate currentDate = LocalDate.now();
        orderService.changeStatusByDate(localeApplication, currentDate);

        List<OrderViewDto> orderViewDtoList = orderService.getAllOrdersWithPagination(localeApplication,
                sorting.getSorting(), sorting.getTypeSorting(), pagination.getStep(), pagination.getPaginationItem());
//        LOGGER.info("orderViewDtoList = {}", orderViewDtoList);
        request.setAttribute("orderList", orderViewDtoList);
        return "manager/admin_page";
    }
}
