package com.example.fricashop.model;

public class Comment {
    int id;
    int blog_id;
    String img_blog;
    String name_blog;
    String desc_blog;
    String date_blog;

    public Comment(int id, int blog_id, String img_blog, String name_blog, String desc_blog, String date_blog) {
        this.id = id;
        this.blog_id = blog_id;
        this.img_blog = img_blog;
        this.name_blog = name_blog;
        this.desc_blog = desc_blog;
        this.date_blog = date_blog;
    }

    public Comment(int id, int blog_id, String img_blog, String name_blog, String desc_blog) {
        this.id = id;
        this.blog_id = blog_id;
        this.img_blog = img_blog;
        this.name_blog = name_blog;
        this.desc_blog = desc_blog;
    }

    public Comment(int blog_id, String img_blog, String name_blog, String desc_blog) {
        this.blog_id = blog_id;
        this.img_blog = img_blog;
        this.name_blog = name_blog;
        this.desc_blog = desc_blog;
    }

    public String getDate_blog() {
        return date_blog;
    }

    public void setDate_blog(String date_blog) {
        this.date_blog = date_blog;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(int blog_id) {
        this.blog_id = blog_id;
    }

    public String getImg_blog() {
        return img_blog;
    }

    public void setImg_blog(String img_blog) {
        this.img_blog = img_blog;
    }

    public String getName_blog() {
        return name_blog;
    }

    public void setName_blog(String name_blog) {
        this.name_blog = name_blog;
    }

    public String getDesc_blog() {
        return desc_blog;
    }

    public void setDesc_blog(String desc_blog) {
        this.desc_blog = desc_blog;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", blog_id=" + blog_id +
                ", img_blog='" + img_blog + '\'' +
                ", name_blog='" + name_blog + '\'' +
                ", desc_blog='" + desc_blog + '\'' +
                ", date_blog='" + date_blog + '\'' +
                '}';
    }
}
