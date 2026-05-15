package com.final_project.cargo_delivery.dao.implementations;

import com.final_project.cargo_delivery.config.DBCPDataSource;
import com.final_project.cargo_delivery.dao.interfaces.UserDao;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.User;
import com.final_project.cargo_delivery.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * UserDao implementation
 *
 * @author Mykhailo Hryb
 */
@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    private static final String SQL_SAVE_USER = "INSERT INTO users (first_name, last_name, email, role_id, password) " +
            "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";

    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE users.id = ?";

    private static final String SQL_GET_ALL_USERS = "SELECT * FROM online_store.users;";

    private static final String SQL_IS_USER_EXIST = "SELECT * FROM users WHERE email = ?;";

    @Override
    public User save(LocaleApplication localeApplication, User user) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new java.util.Locale(localeApplication.getShortName()));
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER, RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setLong(4, user.getRoleId());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setUserId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            LOGGER.error("Can't save entity {}", user);
            LOGGER.error(e.getMessage());
            throw new ApplicationException(messages.getString("exception.error.cannot_save_user") + " " + user);
        }
        return user;
    }

    @Override
    public User getUserById(LocaleApplication localeApplication, long id) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        User resultUser = null;
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                resultUser = setUserFromDataBase(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to load user from DB", e);
            throw new ApplicationException(messages.getString("exception.error.cannot_find_user"));
        }

        return resultUser;
    }

    @Override
    public User findUserByEmail(LocaleApplication localeApplication, String email) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        User resultUser = null;
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                resultUser = setUserFromDataBase(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to load user from DB", e);
            throw new ApplicationException(messages.getString("exception.error.cannot_find_user"));
        }
        if (resultUser == null) {
            LOGGER.error("User not found by email: {}", email);
            throw new ApplicationException(messages.getString("exception.error.cannot_find_user") + ": " + email);
        }
        return resultUser;
    }

    public List<User> getAllUsers(LocaleApplication localeApplication) {
        List<User> result = new ArrayList<>();
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_USERS)) {
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long userId = rs.getLong("id");
                String userFirstName = rs.getString("first_name");
                String userLastName = rs.getString("last_name");
                String userEmail = rs.getString("email");
                int userRoleId = rs.getInt("role_id");
                result.add(new User.Builder().setUserId(userId).setFirstName(userFirstName).setLastName(userLastName)
                        .setEmail(userEmail).setRoleId(userRoleId).build());
            }
        } catch (SQLException e) {
            LOGGER.error("Error while reading all users{}", e.getMessage());
            throw new ApplicationException(messages.getString("exception.error.cannot_find_all_users"));
        }
        return result;
    }

    @Override
    public boolean isUserExist(LocaleApplication localeApplication, String email) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new java.util.Locale(localeApplication.getShortName()));
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_IS_USER_EXIST)) {
            preparedStatement.setString(1, email);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean next = resultSet.next();
            if (next) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to find user with email", e);
            throw new ApplicationException(messages.getString("exception.error.cannot_find_user") + ": " + email);
        }
        return false;
    }

    User setUserFromDataBase(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(Long.parseLong(resultSet.getString("id")));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        user.setRoleId(resultSet.getInt("role_id"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }


}
