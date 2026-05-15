package com.final_project.cargo_delivery.service.interfaces;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.TypeSorting;
import com.final_project.cargo_delivery.web.dto.OrderCreateDto;
import com.final_project.cargo_delivery.web.dto.OrderViewDto;

import java.time.LocalDate;
import java.util.List;

/**
 * OrderService
 *
 * @author Mykhailo Hryb
 */
public interface OrderService {

    /**
     * Saves order
     *
     * @param orderCreateDto
     * @param localeApplication
     */
    void saveOrder(OrderCreateDto orderCreateDto, LocaleApplication localeApplication);

    /**
     * Gets orders for user
     *
     * @param localeApplication
     * @param userId
     * @param orderBy
     * @param typeSorting
     * @param paginationStep
     * @param paginationItem
     * @return OrderViewDto
     */
    List<OrderViewDto> getUserOrders(LocaleApplication localeApplication, long userId, String orderBy,
                                     TypeSorting typeSorting, int paginationStep, int paginationItem);

    /**
     * Gets all orders
     *
     * @param localeApplication
     * @param orderBy
     * @param typeSorting
     * @return OrderViewDto
     */
    List<OrderViewDto> getAllOrders(LocaleApplication localeApplication, String orderBy, TypeSorting typeSorting);

    /**
     * Gets all orders with pagination
     *
     * @param localeApplication
     * @param orderBy
     * @param typeSorting
     * @param paginationStep
     * @param paginationItem
     * @return OrderViewDto
     */
    List<OrderViewDto> getAllOrdersWithPagination(LocaleApplication localeApplication, String orderBy, TypeSorting typeSorting,
                                                  int paginationStep, int paginationItem);

    /**
     * Gets order by id
     *
     * @param localeApplication
     * @param id
     * @return
     */
    OrderViewDto getOrderById(LocaleApplication localeApplication, long id);

    /**
     * Updates path of receipt pdf file
     *
     * @param localeApplication
     * @param id
     * @param receiptPath
     */
    void updateOrderReceiptPath(LocaleApplication localeApplication, long id, String receiptPath);

    /**
     * Changes status of order
     *
     * @param localeApplication
     * @param id
     * @param orderStatus
     */
    void changeOrderStatus(LocaleApplication localeApplication, long id, int orderStatus);

    /**
     * Gets amount of orders in db
     *
     * @param localeApplication
     * @return
     */
    int getCountElements(LocaleApplication localeApplication);


    /**
     * Gets amount of order of user
     *
     * @param localeApplication
     * @param userId
     * @return
     */
    int getCountElementsByUser(LocaleApplication localeApplication, long userId);

    /**
     * Changes status of order by current date.
     * If orders which weren't sent but date of creating has gone then order should be canceled
     * If orders have payed and delivery date has come, order status should be delivered
     *
     * @param localeApplication
     * @param localDate
     */
    void changeStatusByDate(LocaleApplication localeApplication, LocalDate localDate);

    /**
     * Gets first element for pagination
     *
     * @param localeApplication
     * @param paginationStep
     * @param paginationItem
     * @return
     */
    int getFirstElementInPagination(LocaleApplication localeApplication, int paginationStep, int paginationItem);
}
