package com.final_project.cargo_delivery.service.implementation;

import com.final_project.cargo_delivery.converter.OrderConverter;
import com.final_project.cargo_delivery.dao.implementations.OrderDaoImpl;
import com.final_project.cargo_delivery.dao.interfaces.OrderDao;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.Order;
import com.final_project.cargo_delivery.entity.OrderStatusEnum;
import com.final_project.cargo_delivery.entity.TypeSorting;
import com.final_project.cargo_delivery.service.interfaces.OrderService;
import com.final_project.cargo_delivery.web.dto.OrderCreateDto;
import com.final_project.cargo_delivery.web.dto.OrderViewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderService implementation
 *
 * @author Mykhailo Hryb
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderDao orderDao;

    private OrderConverter orderConverter;

    @Autowired
    OrderServiceImpl(OrderDao orderDao, OrderConverter orderConverter) {
        this.orderDao = orderDao;
        this.orderConverter = orderConverter;
    }

    @Override
    public void saveOrder(OrderCreateDto orderCreateDto, LocaleApplication localeApplication) {
        Order order = orderConverter.convertOrderCreateDtoToOrder(orderCreateDto);
        orderDao.saveOrder(order, localeApplication);
    }

    @Override
    public List<OrderViewDto> getUserOrders(LocaleApplication localeApplication, long userId, String orderBy,
                                            TypeSorting typeSorting, int paginationStep, int paginationItem) {
        int firstElem = getFirstElementInPagination(localeApplication, paginationStep, paginationItem);
        List<Order> orderList =
                orderDao.getOrdersByUser(localeApplication, userId, orderBy, typeSorting, firstElem, paginationStep);

        return orderList.stream().map(order -> {
//            orderConverter = new OrderConverter();
            return orderConverter.convertOrderToOrderViewDto(localeApplication, order);
        }).collect(Collectors.toList());
    }

    @Override
    public List<OrderViewDto> getAllOrders(LocaleApplication localeApplication, String orderBy, TypeSorting typeSorting) {
        List<Order> orderList = orderDao.getAllOrders(localeApplication, orderBy, typeSorting);
        return orderList.stream().map(order -> {
//            orderConverter = new OrderConverter();
            return orderConverter.convertOrderToOrderViewDto(localeApplication, order);
        }).collect(Collectors.toList());
    }

    @Override
    public List<OrderViewDto> getAllOrdersWithPagination(LocaleApplication localeApplication, String orderBy,
                                                         TypeSorting typeSorting, int paginationStep, int paginationItem) {

        int firstElem = getFirstElementInPagination(localeApplication, paginationStep, paginationItem);

        LOGGER.info("paginationStep = {}", paginationStep);
        LOGGER.info("paginationItem = {}", paginationItem);
        LOGGER.info("firstElem = {}", firstElem);

        List<Order> orderList = orderDao.getAllOrdersWithPagination(localeApplication, orderBy, typeSorting, firstElem, paginationStep);
        return orderList.stream().map(order -> {
//            orderConverter = new OrderConverter();
            return orderConverter.convertOrderToOrderViewDto(localeApplication, order);
        }).collect(Collectors.toList());
    }

    @Override
    public OrderViewDto getOrderById(LocaleApplication localeApplication, long id) {
        Order order = orderDao.gerOrderById(localeApplication, id);
        return orderConverter.convertOrderToOrderViewDto(localeApplication, order);
    }

    @Override
    public void updateOrderReceiptPath(LocaleApplication localeApplication, long id, String receiptPath) {
        orderDao.updateOrderReceiptPath(localeApplication, id, receiptPath);
    }

    @Override
    public void changeOrderStatus(LocaleApplication localeApplication, long id, int orderStatus) {
        orderDao.changeOrderStatus(localeApplication, id, orderStatus);
    }

    @Override
    public int getCountElements(LocaleApplication localeApplication) {
        return orderDao.getCountElements(localeApplication);
    }

    @Override
    public int getCountElementsByUser(LocaleApplication localeApplication, long userId) {
        return orderDao.getCountElementsByUser(localeApplication, userId);
    }

    @Override
    public void changeStatusByDate(LocaleApplication localeApplication, LocalDate localDate) {
        List<Order> ordersCanBeCanceled = orderDao.getOrdersByStatus(localeApplication, OrderStatusEnum.NOT_REGISTERED.getID());
        List<Order> ordersNotPayed = orderDao.getOrdersByStatus(localeApplication, OrderStatusEnum.NOT_PAYED.getID());
        ordersCanBeCanceled.addAll(ordersNotPayed);

        List<Order> ordersPayed = orderDao.getOrdersByStatus(localeApplication, OrderStatusEnum.PAYED.getID());

        List<Order> ordersExpiredToCancel = ordersCanBeCanceled.stream().filter(
                orderItem -> localDate.isAfter(orderItem.getDateCreated())
        ).collect(Collectors.toList());
        List<Order> ordersDelivered = ordersPayed.stream().filter(
                orderItem -> localDate.isAfter(orderItem.getDateDelivery())
        ).collect(Collectors.toList());

        ordersExpiredToCancel.forEach(orderItem ->
                orderDao.changeOrderStatus(localeApplication, orderItem.getId(), OrderStatusEnum.CANCELED.getID()));
        ordersDelivered.forEach(orderItem ->
                orderDao.changeOrderStatus(localeApplication, orderItem.getId(), OrderStatusEnum.DELIVERED.getID()));

    }

    @Override
    public int getFirstElementInPagination(LocaleApplication localeApplication, int paginationStep, int paginationItem) {
        int firstElementToShow;
        int countElements = orderDao.getCountElements(localeApplication);
        if (countElements < paginationStep * paginationItem) {
            //Get max of entire steps
            firstElementToShow = (countElements / paginationStep) * paginationStep;
        } else {
            firstElementToShow = paginationStep * (paginationItem - 1);
        }
        return firstElementToShow;
    }
}
