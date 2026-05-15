package com.final_project.cargo_delivery.entity;

/**
 * TypeCargo
 *
 * @author Mykhailo Hryb
 */
public class TypeCargo {

    private int id;
    private String name;
    private String imagePath;

    public TypeCargo() {
    }

    public TypeCargo(int id, String name, String imagePath) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
    }

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "TypeCargo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
