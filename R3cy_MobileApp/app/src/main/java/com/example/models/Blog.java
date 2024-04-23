package com.example.models;

import java.util.Date;

public class Blog {
    int blogId;
    String blogTitle;
    String blogAuthor;
    Date blogDate;
    byte[] blogThumb;
    String blogContent;

    public Blog(int blogId, String blogTitle, String blogAuthor, Date blogDate, byte[] blogThumb, String blogContent) {
        this.blogId = blogId;
        this.blogTitle = blogTitle;
        this.blogAuthor = blogAuthor;
        this.blogDate = blogDate;
        this.blogThumb = blogThumb;
        this.blogContent = blogContent;
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

    public String getBlogAuthor() {
        return blogAuthor;
    }

    public void setBlogAuthor(String blogAuthor) {
        this.blogAuthor = blogAuthor;
    }

    public Date getBlogDate() {
        return blogDate;
    }

    public void setBlogDate(Date blogDate) {
        this.blogDate = blogDate;
    }

    public byte[] getBlogThumb() {
        return blogThumb;
    }

    public void setBlogThumb(byte[] blogThumb) {
        this.blogThumb = blogThumb;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }
}
