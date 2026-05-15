package com.final_project.cargo_delivery.web.controller;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.exception.ValidationException;
import com.final_project.cargo_delivery.service.implementation.LocaleServiceImpl;
import com.final_project.cargo_delivery.service.interfaces.LocaleService;
import com.final_project.cargo_delivery.service.interfaces.OrderService;
import com.final_project.cargo_delivery.service.interfaces.ReportService;
import com.final_project.cargo_delivery.web.dto.OrderViewDto;
import com.final_project.cargo_delivery.web.util.Sorting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * MakeReportController.
 *
 * @author Mykhailo Hryb
 */
@Controller
public class MakeReportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MakeReportController.class);
    private ReportService reportService;
    private OrderService orderService;

    @Autowired
    MakeReportController(ReportService reportService, OrderService orderService) {
        this.reportService = reportService;
        this.orderService = orderService;
    }

    @PostMapping("/make-report")
    @ResponseBody
    public String makeReport(HttpServletRequest request) {
        LocaleApplication localeApplication;
        LocaleService localeService = new LocaleServiceImpl();
        Cookie[] arrCookies = request.getCookies();
        localeApplication = localeService.getLocaleByCookieOrDefault(arrCookies);


        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));


        Sorting sorting = new Sorting(request, "order_status_id");
        List<OrderViewDto> orderViewDtoList =
                orderService.getAllOrders(localeApplication, sorting.getSorting(), sorting.getTypeSorting());

        String typeReport = request.getParameter("typeReport");
        String pathToReport = "";
        if (typeReport.equals("report_by_city")) {
            pathToReport = reportService.makeReportByCity(localeApplication, orderViewDtoList);
        } else if (typeReport.equals("report_by_dates")) {
            pathToReport = reportService.makeReportByDate(localeApplication, orderViewDtoList);
        } else {
            throw new ValidationException(messages.getString("exception.error.type_report"));
        }

        request.setAttribute("pathToReport", pathToReport);

        return pathToReport;
    }
}
