package com.final_project.cargo_delivery.web.controller;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.OrderStatusEnum;
import com.final_project.cargo_delivery.service.interfaces.LocaleService;
import com.final_project.cargo_delivery.service.interfaces.OrderService;
import com.final_project.cargo_delivery.service.interfaces.ReceiptPaymentService;
import com.final_project.cargo_delivery.validator.interfaces.ChangeStatusValidator;
import com.final_project.cargo_delivery.validator.interfaces.CreateReceiptPaymentValidator;
import com.final_project.cargo_delivery.web.dto.OrderViewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * CreateReceiptPaymentController.
 *
 * @author Mykhailo Hryb
 */
@Controller
public class CreateReceiptPaymentController {

    private OrderService orderService;
    private ReceiptPaymentService receiptPaymentService;
    private LocaleService localeService;
    private ChangeStatusValidator changeStatusValidator;
    private CreateReceiptPaymentValidator createReceiptPaymentValidator;

    @Autowired
    CreateReceiptPaymentController(OrderService orderService, ReceiptPaymentService receiptPaymentService,
                                   LocaleService localeService, ChangeStatusValidator changeStatusValidator,
                                   CreateReceiptPaymentValidator createReceiptPaymentValidator) {
        this.orderService = orderService;
        this.receiptPaymentService = receiptPaymentService;
        this.localeService = localeService;
        this.changeStatusValidator = changeStatusValidator;
        this.createReceiptPaymentValidator = createReceiptPaymentValidator;
    }

    @PostMapping("/create-receipt-payment")
    @ResponseBody
    public void createReceipt(HttpServletRequest request) {
        LocaleApplication localeApplication;
        Cookie[] arrCookies = request.getCookies();
        localeApplication = localeService.getLocaleByCookieOrDefault(arrCookies);
        String orderIdStr = request.getParameter("orderId");

        //validating orderId
        changeStatusValidator.validateOrderId(localeApplication, orderIdStr);

        long orderId = Long.parseLong(orderIdStr);
        OrderViewDto orderViewDto = orderService.getOrderById(localeApplication, orderId);

        //validating orderStatus
        createReceiptPaymentValidator.validateOrderStatusBeforeCreatingReceipt(localeApplication,
                orderViewDto.getOrderStatusViewDto().getId());

        String receiptPath = receiptPaymentService.createReceiptPaymentForUser(localeApplication, orderViewDto);
        orderService.updateOrderReceiptPath(localeApplication, orderId, receiptPath);

        if (orderViewDto.getOrderStatusViewDto().getId() == OrderStatusEnum.NOT_REGISTERED.getID()) {
            orderService.changeOrderStatus(localeApplication, orderId, OrderStatusEnum.NOT_PAYED.getID());
        }
    }
}
