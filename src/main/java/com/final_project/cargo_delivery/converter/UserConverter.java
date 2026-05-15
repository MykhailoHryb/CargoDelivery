package com.final_project.cargo_delivery.converter;

import com.final_project.cargo_delivery.entity.User;
import com.final_project.cargo_delivery.web.dto.UserCreateDto;
import com.final_project.cargo_delivery.web.dto.UserViewDto;
import org.springframework.stereotype.Component;

/**
 * UserConverter
 *
 * @author Mykhailo Hryb
 */
@Component
public class UserConverter {

    /**
     * Converts UserCreateDto to User
     *
     * @param userCreateDto
     * @return
     */
    public User convertUserCreateDtoAsUser(UserCreateDto userCreateDto) {
        User user = new User();
        user.setEmail(userCreateDto.getEmail());
        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        user.setPassword(userCreateDto.getPassword());
        user.setRoleId(userCreateDto.getRoleId());
        return user;
    }

    /**
     * Converts User to UserViewDto
     *
     * @param user
     * @return
     */
    public UserViewDto convertUserAsUserViewDto(User user) {
        UserViewDto userViewDto = new UserViewDto();
        userViewDto.setId(user.getUserId());
        userViewDto.setEmail(user.getEmail());
        userViewDto.setFirstName(user.getFirstName());
        userViewDto.setLastName(user.getLastName());
        userViewDto.setPassword(user.getPassword());
        userViewDto.setRoleId(user.getRoleId());
        return userViewDto;
    }
}
