package com.example.fricashop.model;

public class Status {
    int id;
    String name;
    String title;
    String image;
    int numberLike;
    int numberComment;

    public Status(int id, String name, String title, String image, int numberLike, int numberComment) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.image = image;
        this.numberLike = numberLike;
        this.numberComment = numberComment;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNumberLike() {
        return numberLike;
    }

    public void setNumberLike(int numberLike) {
        this.numberLike = numberLike;
    }

    public int getNumberComment() {
        return numberComment;
    }

    public void setNumberComment(int numberComment) {
        this.numberComment = numberComment;
    }

    @Override
    public String toString() {
        return "FeedBack{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", numberLike=" + numberLike +
                ", numberComment=" + numberComment +
                '}';
    }
}
