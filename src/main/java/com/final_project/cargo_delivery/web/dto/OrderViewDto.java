package com.final_project.cargo_delivery.web.dto;

import java.time.LocalDate;

/**
 * OrderViewDto
 *
 * @author Mykhailo Hryb
 */
public class OrderViewDto {

    private long id;
    private OrderStatusViewDto orderStatusViewDto;
    private UserViewDto user;
    private int volume;
    private int weight;
    private int price;
    private CityViewDto cityFromViewDto;
    private CityViewDto cityToViewDto;
    private TypeCargoViewDto typeCargoViewDto;
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

    public OrderStatusViewDto getOrderStatusViewDto() {
        return orderStatusViewDto;
    }

    public void setOrderStatusViewDto(OrderStatusViewDto orderStatusViewDto) {
        this.orderStatusViewDto = orderStatusViewDto;
    }

    public UserViewDto getUser() {
        return user;
    }

    public void setUser(UserViewDto user) {
        this.user = user;
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

    public CityViewDto getCityFromViewDto() {
        return cityFromViewDto;
    }

    public void setCityFromViewDto(CityViewDto cityFromViewDto) {
        this.cityFromViewDto = cityFromViewDto;
    }

    public CityViewDto getCityToViewDto() {
        return cityToViewDto;
    }

    public void setCityToViewDto(CityViewDto cityToViewDto) {
        this.cityToViewDto = cityToViewDto;
    }

    public TypeCargoViewDto getTypeCargoViewDto() {
        return typeCargoViewDto;
    }

    public void setTypeCargoViewDto(TypeCargoViewDto typeCargoViewDto) {
        this.typeCargoViewDto = typeCargoViewDto;
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
    public String toString() {
        return "OrderViewDto{" +
                "id=" + id +
                ", orderStatusViewDto=" + orderStatusViewDto +
                ", user=" + user +
                ", volume=" + volume +
                ", weight=" + weight +
                ", price=" + price +
                ", cityFromViewDto=" + cityFromViewDto +
                ", cityToViewDto=" + cityToViewDto +
                ", typeCargoViewDto=" + typeCargoViewDto +
                ", address='" + address + '\'' +
                ", dateDelivery=" + dateDelivery +
                ", receiptPath='" + receiptPath + '\'' +
                '}';
    }
}
