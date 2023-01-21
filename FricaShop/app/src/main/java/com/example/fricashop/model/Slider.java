package com.example.fricashop.model;

public class Slider {
    int id;
    String image;
    String name;

    public Slider(int id, String image, String name) {
        this.id = id;
        this.image = image;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Slider{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
