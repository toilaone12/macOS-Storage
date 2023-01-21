package com.example.youtubedemo.model;

import java.io.Serializable;

public class Video implements Serializable {
    private String name;
    private String image;
    private String nameAuthor;
    private String videoId;

    public Video(String name, String image, String nameAuthor, String videoId) {
        this.name = name;
        this.image = image;
        this.nameAuthor = nameAuthor;
        this.videoId = videoId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
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

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }


    @Override
    public String toString() {
        return "Video{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", nameAuthor='" + nameAuthor + '\'' +
                ", videoId='" + videoId + '\'' +
                '}';
    }
}
