package com.example.shopapp.model;

public class Menu {
    int id;
    String imgMenu;
    String nameMenu;

    public Menu(String imgMenu, String nameMenu) {
        this.imgMenu = imgMenu;
        this.nameMenu = nameMenu;
    }

    public Menu(int id, String nameMenu, String imgMenu) {
        this.id = id;
        this.imgMenu = imgMenu;
        this.nameMenu = nameMenu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgMenu() {
        return imgMenu;
    }

    public void setImgMenu(String imgMenu) {
        this.imgMenu = imgMenu;
    }

    public String getNameMenu() {
        return nameMenu;
    }

    public void setNameMenu(String nameMenu) {
        this.nameMenu = nameMenu;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", imgMenu='" + imgMenu + '\'' +
                ", nameMenu='" + nameMenu + '\'' +
                '}';
    }
}
