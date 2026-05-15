package com.final_project.cargo_delivery.web.dto;

import java.util.Objects;

/**
 * UserViewDto
 *
 * @author Mykhailo Hryb
 */
public class UserViewDto {

    private long id;
    private String firstName;
    private int roleId;
    private String lastName;
    private String email;
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
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
        UserViewDto that = (UserViewDto) o;
        return id == that.id && roleId == that.roleId && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && email.equals(that.email) && password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserViewDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", roleId=" + roleId +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
