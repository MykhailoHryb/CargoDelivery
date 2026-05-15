package com.final_project.cargo_delivery.entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Order
 *
 * @author Mykhailo Hryb
 */
public class Order {

    private long id;
    private int orderStatusId;
    private long userId;
    private int volume;
    private int weight;
    private int price;
    private int cityFromId;
    private int cityToId;
    private int typeCargoId;
    private String address;
    private LocalDate dateCreated;
    private LocalDate dateDelivery;
    private String receiptPath;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCityFromId() {
        return cityFromId;
    }

    public void setCityFromId(int cityFromId) {
        this.cityFromId = cityFromId;
    }

    public int getCityToId() {
        return cityToId;
    }

    public void setCityToId(int cityToId) {
        this.cityToId = cityToId;
    }

    public int getTypeCargoId() {
        return typeCargoId;
    }

    public void setTypeCargoId(int typeCargoId) {
        this.typeCargoId = typeCargoId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateDelivery() {
        return dateDelivery;
    }

    public void setDateDelivery(LocalDate dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public String getReceiptPath() {
        return receiptPath;
    }

    public void setReceiptPath(String receiptPath) {
        this.receiptPath = receiptPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && userId == order.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderStatusId=" + orderStatusId +
                ", userId=" + userId +
                ", volume=" + volume +
                ", weight=" + weight +
                ", price=" + price +
                ", cityFromId=" + cityFromId +
                ", cityToId=" + cityToId +
                ", typeCargoId=" + typeCargoId +
                ", address='" + address + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateDelivery=" + dateDelivery +
                ", receiptPath='" + receiptPath + '\'' +
                '}';
    }
}
