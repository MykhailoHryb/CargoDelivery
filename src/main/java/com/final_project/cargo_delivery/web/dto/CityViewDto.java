package com.final_project.cargo_delivery.web.dto;

import java.util.Objects;

/**
 * CityViewDto
 *
 * @author Mykhailo Hryb
 */
public class CityViewDto {

    private int id;
    private String name;
    private String imgPath;
    private boolean isForeign;
    private double latitude;
    private double longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public boolean getIsForeign() {
        return isForeign;
    }

    public void setIsForeign(boolean isForeign) {
        this.isForeign = isForeign;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityViewDto that = (CityViewDto) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CityViewDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", isForeign=" + isForeign +
                '}';
    }
}
