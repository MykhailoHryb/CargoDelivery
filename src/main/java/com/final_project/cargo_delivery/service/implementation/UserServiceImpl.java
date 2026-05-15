package com.final_project.cargo_delivery.service.implementation;

import com.final_project.cargo_delivery.converter.UserConverter;
import com.final_project.cargo_delivery.dao.implementations.UserDaoImpl;
import com.final_project.cargo_delivery.dao.interfaces.UserDao;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.User;
import com.final_project.cargo_delivery.service.interfaces.UserService;
import com.final_project.cargo_delivery.web.dto.UserCreateDto;
import com.final_project.cargo_delivery.web.dto.UserViewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserService implementation
 *
 * @author Mykhailo Hryb
 */
@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    private UserConverter userConverter;

    @Autowired
    UserServiceImpl(UserDao userDao, UserConverter userConverter) {
        this.userDao = userDao;
        this.userConverter = userConverter;
    }

    @Override
    public UserViewDto register(LocaleApplication localeApplication, UserCreateDto userCreateDto) {
        User user = userConverter.convertUserCreateDtoAsUser(userCreateDto);
        System.out.println("user after convert = " + user);
        user = userDao.save(localeApplication, user);
        System.out.println("user after dao = " + user);
        return userConverter.convertUserAsUserViewDto(user);
    }

    @Override
    public UserViewDto login(LocaleApplication localeApplication, String email, String password) {
        User user = userDao.findUserByEmail(localeApplication, email);
        return userConverter.convertUserAsUserViewDto(user);
    }

    @Override
    public UserViewDto getUserById(LocaleApplication localeApplication, long id) {
        User user = userDao.getUserById(localeApplication, id);
        return userConverter.convertUserAsUserViewDto(user);
    }

    @Override
    public List<UserViewDto> getAllUsers(LocaleApplication localeApplication) {
        List<User> users = userDao.getAllUsers(localeApplication);
        return users.stream().map(user -> userConverter.convertUserAsUserViewDto(user)).collect(Collectors.toList());
    }
}
