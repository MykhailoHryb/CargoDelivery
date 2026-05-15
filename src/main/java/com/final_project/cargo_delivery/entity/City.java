package com.final_project.cargo_delivery.entity;

import java.util.Objects;

/**
 * City
 *
 * @author Mykhailo Hryb
 */
public class City {

    private int id;

    private String name;

    private double latitude;

    private double longitude;

    private String imgPath;

    private boolean isForeign;

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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public boolean getIsForeign() {
        return isForeign;
    }

    public void setIsForeign(boolean foreign) {
        isForeign = foreign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id == city.id && Double.compare(city.latitude, latitude) == 0 && Double.compare(city.longitude, longitude) == 0 && name.equals(city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", imgPath='" + imgPath + '\'' +
                ", isForeign=" + isForeign +
                '}';
    }
}
