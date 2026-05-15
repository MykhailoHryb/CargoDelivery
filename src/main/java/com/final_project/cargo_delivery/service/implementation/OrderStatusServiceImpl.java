package com.final_project.cargo_delivery.service.implementation;

import com.final_project.cargo_delivery.converter.OrderStatusConverter;
import com.final_project.cargo_delivery.dao.implementations.OrderStatusDaoImpl;
import com.final_project.cargo_delivery.dao.interfaces.OrderStatusDao;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.OrderStatus;
import com.final_project.cargo_delivery.service.interfaces.OrderStatusService;
import com.final_project.cargo_delivery.web.dto.OrderStatusViewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OrderStatusService implementation
 *
 * @author Mykhailo Hryb
 */
@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    private OrderStatusDao orderStatusDao;
    private OrderStatusConverter orderStatusConverter;

    @Autowired
    OrderStatusServiceImpl(OrderStatusDao orderStatusDao, OrderStatusConverter orderStatusConverter) {
        this.orderStatusDao = orderStatusDao;
        this.orderStatusConverter = orderStatusConverter;
    }

    @Override
    public OrderStatusViewDto getOrderStatusById(LocaleApplication localeApplication, int id) {
        OrderStatus orderStatus = orderStatusDao.getOrderStatusById(localeApplication, id);
        return orderStatusConverter.convertOrderStatusToOrderStatusViewDto(orderStatus);
    }
}
