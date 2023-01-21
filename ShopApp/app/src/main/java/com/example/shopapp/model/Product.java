package com.example.shopapp.model;

public class Product {
    private int idProduct;
    private int idCate;
    private String name;
    private int price;
    private int quantity;
    private String image;
    private String desc;

    public Product() {
    }

    public Product(int idProduct, int idCate, String name, int price, int quantity, String image, String desc) {
        this.idProduct = idProduct;
        this.idCate = idCate;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.desc = desc;
    }

    public Product(int idProduct,int idCate, String name, int price, String image, String desc) {
        this.idProduct = idProduct;
        this.idCate = idCate;
        this.name = name;
        this.price = price;
        this.image = image;
        this.desc = desc;
    }

    public Product(int idCate,String name, int price, String image) {
        this.idCate = idCate;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Product(String name, int price, int quantity, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }

    public Product(String name, int price, String image, String desc) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.desc = desc;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", idCate=" + idCate +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", image='" + image + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
