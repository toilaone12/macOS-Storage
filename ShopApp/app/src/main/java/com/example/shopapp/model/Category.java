package com.example.shopapp.model;

public class Category {
    private int idCate;
    private String imgCate;
    private String nameCate;

    public Category(int idCate, String imgCate, String nameCate) {
        this.idCate = idCate;
        this.imgCate = imgCate;
        this.nameCate = nameCate;
    }

    public Category() {
    }

    public int getIdCate() {
        return idCate;
    }

    public void setIdCate(int idCate) {
        this.idCate = idCate;
    }

    public String getImgCate() {
        return imgCate;
    }

    public void setImgCate(String imgCate) {
        this.imgCate = imgCate;
    }

    public String getNameCate() {
        return nameCate;
    }

    public void setNameCate(String nameCate) {
        this.nameCate = nameCate;
    }

    @Override
    public String toString() {
        return "Category{" +
                "idCate=" + idCate +
                ", imgCate='" + imgCate + '\'' +
                ", nameCate='" + nameCate + '\'' +
                '}';
    }
}
