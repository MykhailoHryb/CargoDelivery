package com.final_project.cargo_delivery.converter;

import com.final_project.cargo_delivery.entity.OrderStatus;
import com.final_project.cargo_delivery.web.dto.OrderStatusViewDto;
import org.springframework.stereotype.Component;

/**
 * OrderStatusConverter
 *
 * @author Mykhailo Hryb
 */
@Component
public class OrderStatusConverter {

    /**
     * Converts OrderStatus to OrderStatusViewDto
     *
     * @param orderStatus
     * @return
     */
    public OrderStatusViewDto convertOrderStatusToOrderStatusViewDto(OrderStatus orderStatus) {
        OrderStatusViewDto orderStatusViewDto = new OrderStatusViewDto();
        orderStatusViewDto.setId(orderStatus.getOrderStatusId());
        orderStatusViewDto.setName(orderStatus.getName());
        orderStatusViewDto.setImagePath(orderStatus.getImagePath());
        return orderStatusViewDto;
    }
}
