package com.bruteforces.demo.entity;

import java.sql.Timestamp;

public class Blog {
    private int blogId;
    private String blogTitle;
    private String blogSrc;
    private Timestamp blogTime;

    public Blog() {

    }

    public Blog(int blogId, String blogTitle, String blogSrc, Timestamp blogTime) {
        this.blogId = blogId;
        this.blogTitle = blogTitle;
        this.blogSrc = blogSrc;
        this.blogTime = blogTime;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogSrc() {
        return blogSrc;
    }

    public void setBlogSrc(String blogSrc) {
        this.blogSrc = blogSrc;
    }

    public Timestamp getBlogTime() {
        return blogTime;
    }

    public void setBlogTime(Timestamp blogTime) {
        this.blogTime = blogTime;
    }
}
