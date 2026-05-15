package com.final_project.cargo_delivery.dao.implementations;

import com.final_project.cargo_delivery.config.DBCPDataSource;
import com.final_project.cargo_delivery.dao.interfaces.OrderStatusDao;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.OrderStatus;
import com.final_project.cargo_delivery.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * OrderStatusDao implementation
 *
 * @author Mykhailo Hryb
 */
@Repository
public class OrderStatusDaoImpl implements OrderStatusDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderStatusDaoImpl.class);

    private static final String GET_ORDER_STATUS_BY_ID = "SELECT order_statuses.*,  order_statuses_l10n.name " +
            "FROM order_statuses INNER JOIN order_statuses_l10n ON order_statuses.id = order_statuses_l10n.order_status_id " +
            "WHERE locale_id = ? AND order_statuses.id = ?";

    @Override
    public OrderStatus getOrderStatusById(LocaleApplication localeApplication, int id) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        OrderStatus orderStatus = new OrderStatus();
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_STATUS_BY_ID)) {
            preparedStatement.setInt(1, localeApplication.getLocaleId());
            preparedStatement.setInt(2, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orderStatus.setOrderStatusId(resultSet.getInt("id"));
                orderStatus.setName(resultSet.getString("name"));
                orderStatus.setImagePath(resultSet.getString("img_path"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            LOGGER.error("Can't get order status {}", sqlException.getMessage());
            throw new ApplicationException(messages.getString("exception.error.cannot_save_order_status"));
        }
        return orderStatus;
    }
}
