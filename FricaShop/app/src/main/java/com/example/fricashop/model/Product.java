package com.example.fricashop.model;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private int cate_id;
    private String name;
    private int quantity;
    private int price;
    private String image;
    private String link_ytb;
    private String desc;

    public Product(int id, int cate_id, String name, int quantity, int price, String image,String link_ytb, String desc) {
        this.id = id;
        this.cate_id = cate_id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.link_ytb = link_ytb;
        this.desc = desc;
    }

    public String getLink_ytb() {
        return link_ytb;
    }

    public void setLink_ytb(String link_ytb) {
        this.link_ytb = link_ytb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCate_id() {
        return cate_id;
    }

    public void setCate_id(int cate_id) {
        this.cate_id = cate_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
                "id=" + id +
                ", cate_id=" + cate_id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", link_ytb='" + link_ytb + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
