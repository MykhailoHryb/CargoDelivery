package com.final_project.cargo_delivery.web.dto;

import java.util.Objects;

/**
 * TypeCargoViewDto sets while registration new user
 *
 * @author Mykhailo Hryb
 */
public class UserCreateDto {

    private String firstName;
    private String lastName;
    private String email;
    int roleId;
    private String password;

    public UserCreateDto(String firstName, String lastName, String email, int roleId, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roleId = roleId;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCreateDto that = (UserCreateDto) o;
        return roleId == that.roleId && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && email.equals(that.email) && password.equals(that.password);
    }


    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, roleId, password);
    }

    @Override
    public String toString() {
        return "UserCreateDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", roleId=" + roleId +
                ", password='" + password + '\'' +
                '}';
    }
}
