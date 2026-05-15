package com.final_project.cargo_delivery.service.interfaces;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.web.dto.OrderViewDto;

/**
 * OrderStatusService
 *
 * @author Mykhailo Hryb
 */
public interface ReceiptPaymentService {

    /**
     * Creates pdf file of receipt of payment
     *
     * @param localeApplication
     * @param orderViewDto
     * @return
     */
    String createReceiptPaymentForUser(LocaleApplication localeApplication, OrderViewDto orderViewDto);
}
