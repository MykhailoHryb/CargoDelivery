package com.final_project.cargo_delivery.service.interfaces;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.web.dto.UserCreateDto;
import com.final_project.cargo_delivery.web.dto.UserViewDto;

import java.util.List;

/**
 * UserService
 *
 * @author Mykhailo Hryb
 */
public interface UserService {

    /**
     * Registers new user
     *
     * @param localeApplication
     * @param userCreateDto
     * @return
     */
    UserViewDto register(LocaleApplication localeApplication, UserCreateDto userCreateDto);


    /**
     * Logins user
     *
     * @param localeApplication
     * @param email
     * @param password
     * @return
     */
    UserViewDto login(LocaleApplication localeApplication, String email, String password);

    /**
     * Gets user by id
     *
     * @param localeApplication
     * @param id
     * @return
     */
    UserViewDto getUserById(LocaleApplication localeApplication, long id);

    /**
     * Gets all users
     *
     * @param localeApplication
     * @return
     */
    List<UserViewDto> getAllUsers(LocaleApplication localeApplication);
}
