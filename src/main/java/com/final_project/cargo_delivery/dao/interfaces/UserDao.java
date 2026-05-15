package com.final_project.cargo_delivery.dao.interfaces;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.User;

import java.util.List;

/**
 * Data access object for User entity
 *
 * @author Mykhailo Hryb
 */
public interface UserDao {

    /**
     * Finds user by email
     *
     * @param localeApplication
     * @param email
     * @return
     */
    User findUserByEmail(LocaleApplication localeApplication, String email);

    /**
     * Checks if user exist in database
     *
     * @param localeApplication
     * @param email
     * @return
     */
    boolean isUserExist(LocaleApplication localeApplication, String email);

    /**
     * Gets all users from database
     *
     * @param localeApplication
     * @return
     */
    List<User> getAllUsers(LocaleApplication localeApplication);

    /**
     * Saves user to database
     *
     * @param localeApplication
     * @param user
     * @return
     */
    User save(LocaleApplication localeApplication, User user);

    /**
     * Gets user by id
     *
     * @param localeApplication
     * @param id
     * @return
     */
    User getUserById(LocaleApplication localeApplication, long id);
}
