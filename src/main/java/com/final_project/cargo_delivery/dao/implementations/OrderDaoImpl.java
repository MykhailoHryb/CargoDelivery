package com.final_project.cargo_delivery.dao.implementations;

import com.final_project.cargo_delivery.config.DBCPDataSource;
import com.final_project.cargo_delivery.dao.interfaces.OrderDao;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.Order;
import com.final_project.cargo_delivery.entity.TypeSorting;
import com.final_project.cargo_delivery.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.*;
import java.util.*;

/**
 * OrderDao implementation
 *
 * @author Mykhailo Hryb
 */
@Repository
public class OrderDaoImpl implements OrderDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDaoImpl.class);

    private static final String SAVE_ORDER = "INSERT INTO orders " +
            "(order_status_id, user_id, volume, weight, price, city_form_id, city_to_id, type_cargo_id, address," +
            "date_created, date_delivered) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String GET_ALL_ORDERS = "SELECT * FROM orders ORDER BY #order_column# #type_sorting#";

    private static final String GET_ALL_ORDERS_WITH_PAGINATION = "SELECT * FROM orders ORDER BY #order_column# #type_sorting# LIMIT ?, ?";

    private static final String GET_ALL_ORDERS_FOR_USER = "SELECT * FROM orders WHERE user_id = ? " +
            "ORDER BY #order_column# #type_sorting# LIMIT ?, ?";

    private static final String GET_ORDERS_BY_STATUS = "SELECT * FROM orders WHERE order_status_id = ? ";

    private static final String GET_ORDER_BY_ID = "SELECT * FROM orders WHERE orders.id = ?";

    private static final String UPDATE_ORDER_RECEIPT_PATH = "UPDATE orders " +
            "SET receipt_path = ? WHERE orders.id = ?";

    private static final String CHANGE_ORDER_STATUS = "UPDATE orders SET order_status_id = ? WHERE orders.id = ?";

    private static final String GET_COUNT_ELEMENTS = "SELECT COUNT(*) AS total FROM orders";

    private static final String GET_COUNT_ELEMENTS_BY_USER = "SELECT COUNT(*) AS total FROM orders WHERE user_id = ?";

    private static final List<String> COLUMNS_TO_ORDER = Arrays.asList("id", "order_status_id", "volume",
            "weight", "price", "city_form_id", "city_to_id", "date_created", "date_delivered", "type_cargo_id", "user_id");

    @Override
    public List<Order> getAllOrders(LocaleApplication localeApplication, String orderBy, TypeSorting typeSorting) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));

        if (!COLUMNS_TO_ORDER.contains(orderBy)) {
            throw new ApplicationException(messages.getString("exception.error.error_sorting"));
        }

        String sqlWithSort = GET_ALL_ORDERS.replace("#order_column#", orderBy);

        List<Order> orders = new ArrayList<>();
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlWithSort)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = fillOrderFromDataBase(resultSet);
                orders.add(order);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Can't get orders {}", sqlException.getMessage());
            throw new ApplicationException(messages.getString("exception.error.cannot_get_orders"));
        }
        return orders;
    }

    @Override
    public List<Order> getAllOrdersWithPagination(LocaleApplication localeApplication, String orderBy, TypeSorting typeSorting,
                                                  int firstElem, int paginationStep) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));

        if (!COLUMNS_TO_ORDER.contains(orderBy)) {
            throw new ApplicationException(messages.getString("exception.error.error_sorting"));
        }

        String sqlWithSort = GET_ALL_ORDERS_WITH_PAGINATION.replace("#order_column#", orderBy);
        sqlWithSort = sqlWithSort.replace("#type_sorting#", typeSorting.name());

        List<Order> orders = new ArrayList<>();
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlWithSort)) {
            preparedStatement.setInt(1, firstElem);
            preparedStatement.setInt(2, paginationStep);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = fillOrderFromDataBase(resultSet);
                orders.add(order);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Can't get orders {}", sqlException.getMessage());
            throw new ApplicationException(messages.getString("exception.error.cannot_get_orders"));
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersByUser(LocaleApplication localeApplication, long userId, String orderBy,
                                       TypeSorting typeSorting, int firstElem, int paginationStep) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));

        if (!COLUMNS_TO_ORDER.contains(orderBy)) {
            throw new ApplicationException(messages.getString("exception.error.error_sorting"));
        }

        String sqlWithSort = GET_ALL_ORDERS_FOR_USER.replace("#order_column#", orderBy);
        sqlWithSort = sqlWithSort.replace("#type_sorting#", typeSorting.name());


        List<Order> orders = new ArrayList<>();
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlWithSort)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setInt(2, firstElem);
            preparedStatement.setInt(3, paginationStep);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = fillOrderFromDataBase(resultSet);
//                System.out.println("order for user = " + order);
                orders.add(order);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Can't get orders {}", sqlException.getMessage());
            throw new ApplicationException(messages.getString("exception.error.cannot_get_orders"));
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersByStatus(LocaleApplication localeApplication, int orderStatus) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDERS_BY_STATUS)) {
            preparedStatement.setLong(1, orderStatus);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = fillOrderFromDataBase(resultSet);
                orders.add(order);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Can't get orders {}", sqlException.getMessage());
            throw new ApplicationException(messages.getString("exception.error.cannot_get_orders"));
        }
        return orders;
    }

    @Override
    public void saveOrder(Order order, LocaleApplication localeApplication) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));

        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_ORDER)) {
            preparedStatement.setInt(1, order.getOrderStatusId());
            preparedStatement.setDouble(2, order.getUserId());
            preparedStatement.setInt(3, order.getVolume());
            preparedStatement.setInt(4, order.getWeight());
            preparedStatement.setInt(5, order.getPrice());
            preparedStatement.setInt(6, order.getCityFromId());
            preparedStatement.setInt(7, order.getCityToId());
            preparedStatement.setInt(8, order.getTypeCargoId());
            preparedStatement.setString(9, order.getAddress());
            preparedStatement.setDate(10, Date.valueOf(order.getDateCreated()));
            preparedStatement.setDate(11, Date.valueOf(order.getDateDelivery()));

            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            LOGGER.error("Can't save entity", sqlException);
            throw new ApplicationException(messages.getString("exception.error.cannot_save_order"));
        }
    }

    @Override
    public Order gerOrderById(LocaleApplication localeApplication, long id) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        Order order = new Order();
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                order = fillOrderFromDataBase(resultSet);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Can't get order", sqlException);
            throw new ApplicationException(messages.getString("exception.error.cannot_find_order"));
        }
        return order;
    }

    @Override
    public void updateOrderReceiptPath(LocaleApplication localeApplication, long id, String receiptPath) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_RECEIPT_PATH)) {
            preparedStatement.setString(1, receiptPath);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            LOGGER.error("Can't save receipt path {}", sqlException.getMessage());
            throw new ApplicationException(messages.getString("exception.error.cannot_save_receipt"));
        }
    }

    @Override
    public void changeOrderStatus(LocaleApplication localeApplication, long id, int orderStatus) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_ORDER_STATUS)) {
            preparedStatement.setInt(1, orderStatus);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            LOGGER.error("Can't save order status {}", sqlException.getMessage());
            throw new ApplicationException(messages.getString("exception.error.cannot_save_order_status "));
        }
    }

    @Override
    public int getCountElements(LocaleApplication localeApplication) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        int countElements = 0;
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_COUNT_ELEMENTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                countElements = resultSet.getInt("total");
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Can't get count elements {}", sqlException.getMessage());
            throw new ApplicationException(messages.getString("exception.error.count_elements"));
        }
        return countElements;
    }

    @Override
    public int getCountElementsByUser(LocaleApplication localeApplication, long userId) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        int countElements = 0;
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_COUNT_ELEMENTS_BY_USER)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                countElements = resultSet.getInt("total");
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Can't get count elements {}", sqlException.getMessage());
            throw new ApplicationException(messages.getString("exception.error.count_elements"));
        }
        return countElements;
    }

    private Order fillOrderFromDataBase(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getInt("id"));
        order.setOrderStatusId(resultSet.getInt("order_status_id"));
        order.setUserId(resultSet.getLong("user_id"));
        order.setVolume(resultSet.getInt("volume"));
        order.setWeight(resultSet.getInt("weight"));
        order.setPrice(resultSet.getInt("price"));
        order.setCityFromId(resultSet.getInt("city_form_id"));
        order.setCityToId(resultSet.getInt("city_to_id"));
        order.setTypeCargoId(resultSet.getInt("type_cargo_id"));
        order.setAddress(resultSet.getString("address"));
        Date dateCreated = resultSet.getDate("date_created");
        order.setDateCreated(dateCreated.toLocalDate());
        Date dateDelivered = resultSet.getDate("date_delivered");
        order.setDateDelivery(dateDelivered.toLocalDate());
        order.setReceiptPath(resultSet.getString("receipt_path"));
        return order;
    }
}
