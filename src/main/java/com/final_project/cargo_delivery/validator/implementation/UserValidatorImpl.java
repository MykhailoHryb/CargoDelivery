package com.final_project.cargo_delivery.validator.implementation;

import com.final_project.cargo_delivery.dao.implementations.UserDaoImpl;
import com.final_project.cargo_delivery.dao.interfaces.UserDao;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.exception.ValidationException;
import com.final_project.cargo_delivery.validator.interfaces.UserValidator;
import com.final_project.cargo_delivery.web.dto.UserCreateDto;
import com.final_project.cargo_delivery.web.dto.UserViewDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * UserValidator implementation
 *
 * @author Mykhailo Hryb
 */
@Component
public class UserValidatorImpl implements UserValidator {

    private UserDao userDao;

    UserValidatorImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void validateUserCredentials(LocaleApplication localeApplication, String email, String password) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            throw new ValidationException(messages.getString("exception.error.invalid_credentials"));
        }
    }

    @Override
    public void validateNewUser(LocaleApplication localeApplication, UserCreateDto createDto) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        boolean isUserExist = userDao.isUserExist(localeApplication, createDto.getEmail());

        if (isUserExist) {
            throw new ValidationException(messages.getString("exception.error.user_exist"));
        }

        if (StringUtils.isEmpty(createDto.getFirstName())) {
            throw new ValidationException(messages.getString("exception.error.invalid_user_name") + createDto.getFirstName());
        }
    }

    @Override
    public void isCorrectPasswordAndEmail(LocaleApplication localeApplication, UserViewDto userViewDto, String email, String password) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new java.util.Locale(localeApplication.getShortName()));
        if (!userViewDto.getPassword().equals(password) || !userViewDto.getEmail().equals(email)) {
            throw new ValidationException(messages.getString("exception.error.wrong_password"));
        }
    }
}
