package com.final_project.cargo_delivery.web.controller;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.OrderStatusEnum;
import com.final_project.cargo_delivery.service.implementation.LocaleServiceImpl;
import com.final_project.cargo_delivery.service.interfaces.LocaleService;
import com.final_project.cargo_delivery.service.interfaces.OrderService;
import com.final_project.cargo_delivery.validator.implementation.ChangeStatusValidatorImpl;
import com.final_project.cargo_delivery.validator.interfaces.ChangeStatusValidator;
import com.final_project.cargo_delivery.web.dto.OrderViewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * ChangeOrderStatusController changes status of order depended of form
 *
 * @author Mykhailo Hryb
 */
@Controller
public class ChangeOrderStatusController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeOrderStatusController.class);

    private OrderService orderService;

    @Autowired
    ChangeOrderStatusController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/change-order-status")
    @ResponseBody
    public void changeOrderStatus(HttpServletRequest request) {

        LocaleApplication localeApplication;
        LocaleService localeService = new LocaleServiceImpl();
        Cookie[] arrCookies = request.getCookies();
        localeApplication = localeService.getLocaleByCookieOrDefault(arrCookies);

        String orderStatusRequest = request.getParameter("orderStatus");

        ChangeStatusValidator changeStatusValidator = new ChangeStatusValidatorImpl();
        String orderIdStr = request.getParameter("orderId");
        changeStatusValidator.validateOrderId(localeApplication, orderIdStr);

        long orderId = Long.parseLong(orderIdStr);
        int orderStatusId = -1;
        OrderViewDto orderViewDto = orderService.getOrderById(localeApplication, orderId);
        LOGGER.info("orderViewDto {}", orderViewDto);

        if (orderStatusRequest.equals("cancel")) {
            orderStatusId = OrderStatusEnum.CANCELED.getID();
            changeStatusValidator.validateStatusToCancel(localeApplication,
                    orderViewDto.getOrderStatusViewDto().getId());
        } else if (orderStatusRequest.equals("pay")) {
            orderStatusId = OrderStatusEnum.PAYED.getID();
            changeStatusValidator.validateStatusToPay(localeApplication,
                    orderViewDto.getOrderStatusViewDto().getId(), orderViewDto.getReceiptPath());
        } else {
            changeStatusValidator.validateTypeOfChangingStatus(localeApplication, orderStatusRequest);
        }

        orderService.changeOrderStatus(localeApplication, orderId, orderStatusId);
    }
}
