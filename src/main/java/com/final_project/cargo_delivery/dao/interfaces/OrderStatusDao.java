package com.final_project.cargo_delivery.dao.interfaces;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.OrderStatus;

/**
 * Data access object for OrderStatus entity
 *
 * @author Mykhailo Hryb
 */
public interface OrderStatusDao {

    /**
     * Gets status of Order by id
     *
     * @param localeApplication
     * @param id
     * @return
     */
    OrderStatus getOrderStatusById(LocaleApplication localeApplication, int id);
}
