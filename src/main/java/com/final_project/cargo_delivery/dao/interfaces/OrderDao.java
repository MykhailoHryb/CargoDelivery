package com.final_project.cargo_delivery.dao.interfaces;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.Order;
import com.final_project.cargo_delivery.entity.TypeSorting;

import java.util.List;

/**
 * Data access object for Order entity
 *
 * @author Mykhailo Hryb
 */
public interface OrderDao {

    /**
     * Gets all orders from database
     *
     * @param localeApplication
     * @param orderBy
     * @param typeSorting
     * @return
     */
    List<Order> getAllOrders(LocaleApplication localeApplication, String orderBy, TypeSorting typeSorting);

    /**
     * Gets all orders with pagination from database
     *
     * @param localeApplication
     * @param orderBy
     * @param typeSorting
     * @param firstElem
     * @param paginationStep
     * @return
     */
    List<Order> getAllOrdersWithPagination(LocaleApplication localeApplication, String orderBy, TypeSorting typeSorting,
                                           int firstElem, int paginationStep);

    /**
     * Gets orders of user from database
     *
     * @param localeApplication
     * @param userId
     * @param orderBy
     * @param typeSorting
     * @param firstElem
     * @param paginationStep
     * @return
     */
    List<Order> getOrdersByUser(LocaleApplication localeApplication, long userId, String orderBy,
                                TypeSorting typeSorting, int firstElem, int paginationStep);

    /**
     * Get orders with status from database
     *
     * @param localeApplication
     * @param orderStatus
     * @return
     */
    List<Order> getOrdersByStatus(LocaleApplication localeApplication, int orderStatus);

    /**
     * Saves order to database
     *
     * @param order
     * @param localeApplication
     */
    void saveOrder(Order order, LocaleApplication localeApplication);

    /**
     * Gets order by id from database
     *
     * @param localeApplication
     * @param id
     * @return
     */
    Order gerOrderById(LocaleApplication localeApplication, long id);

    /**
     * Updates receipt path of order
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
     * Gets amount of orders
     *
     * @param localeApplication
     * @return
     */
    int getCountElements(LocaleApplication localeApplication);

    /**
     * Gets amount of orders of user
     *
     * @param localeApplication
     * @param userId
     * @return
     */
    int getCountElementsByUser(LocaleApplication localeApplication, long userId);
}
