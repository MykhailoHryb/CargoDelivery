package com.final_project.cargo_delivery.service.interfaces;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.web.dto.OrderStatusViewDto;

/**
 * OrderStatusService
 *
 * @author Mykhailo Hryb
 */
public interface OrderStatusService {

    /**
     * Gets order status by id
     *
     * @param localeApplication
     * @param id
     * @return OrderStatusViewDto
     */
    OrderStatusViewDto getOrderStatusById(LocaleApplication localeApplication, int id);
}
