package com.final_project.cargo_delivery.validator.interfaces;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.web.dto.UserCreateDto;
import com.final_project.cargo_delivery.web.dto.UserViewDto;

/**
 * UserValidator
 *
 * @author Mykhailo Hryb
 */
public interface UserValidator {

    /**
     * Validates email and password
     *
     * @param localeApplication
     * @param email
     * @param password
     */
    void validateUserCredentials(LocaleApplication localeApplication, String email, String password);

    /**
     * Validates data for new user
     *
     * @param localeApplication
     * @param createDto
     */
    void validateNewUser(LocaleApplication localeApplication, UserCreateDto createDto);

    /**
     * Validates if email and password the same
     *
     * @param localeApplication
     * @param userViewDto
     * @param email
     * @param password
     */
    void isCorrectPasswordAndEmail(LocaleApplication localeApplication, UserViewDto userViewDto, String email, String password);

}
