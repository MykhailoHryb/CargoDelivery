package com.final_project.cargo_delivery.entity;

/**
 * OrderStatus
 *
 * @author Mykhailo Hryb
 */
public class OrderStatus {

    private int orderStatusId;
    private String name;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    private String imagePath;

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "orderStatusId=" + orderStatusId +
                ", name='" + name + '\'' +
                '}';
    }
}
