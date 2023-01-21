package com.example.mvpdemo.model;

public class User {
    private int id;
    private String userName;
    private String name;
    private String tuoi;
    private String passWord;

    public User(int id, String userName, String passWord) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
    }


    public User(String userName, String name, String tuoi, String passWord) {
        this.userName = userName;
        this.name = name;
        this.tuoi = tuoi;
        this.passWord = passWord;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", tuoi='" + tuoi + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }

    public String getTuoi() {
        return tuoi;
    }

    public void setTuoi(String tuoi) {
        this.tuoi = tuoi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
