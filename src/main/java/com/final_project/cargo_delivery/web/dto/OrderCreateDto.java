package com.final_project.cargo_delivery.web.dto;

import java.time.LocalDate;

/**
 * OrderCreateDto sets while adding new order
 *
 * @author Mykhailo Hryb
 */
public class OrderCreateDto {

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

    @Override
    public String toString() {
        return "OrderCreateDto{" +
                "orderStatusId=" + orderStatusId +
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
                '}';
    }
}
