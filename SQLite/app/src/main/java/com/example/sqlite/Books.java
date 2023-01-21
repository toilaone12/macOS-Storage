package com.example.sqlite;

public class Books {
    private int id;
    private String name;
    private int page;
    private int price;
    private String desc;
    private byte[] image;

    public Books() {

    }



    public Books(int id, String name, int page, int price, String desc, byte[] image) {
        this.id = id;
        this.name = name;
        this.page = page;
        this.price = price;
        this.desc = desc;
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return
                "id=" + id + ", image="+image+
                ", name='" + name + '\'' +
                ", page=" + page +
                ", price=" + price +
                ", desc='" + desc + '\'' ;
    }
}
