package com.example.fricashop.model;

import java.io.Serializable;

public class Category implements Serializable {
    private int idCate;
    private String name;
    private String image;

    public Category(int idCate, String name, String image) {
        this.idCate = idCate;
        this.name = name;
        this.image = image;
    }

    public int getIdCate() {
        return idCate;
    }

    public void setIdCate(int idCate) {
        this.idCate = idCate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Category{" +
                "idCate=" + idCate +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
